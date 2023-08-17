<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import type { Row, ScheduleTask } from '$lib/types/core.type';
	import { createForm } from 'svelte-forms-lib';
	import { createTask, getRows } from '$lib/schedule.service';
	import { error, success } from '$lib/notification';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import DateTimeInput from '$lib/components/core/DateTimeInput.svelte';
	import RowSelectInput from '$lib/components/schedule/RowSelectInput.svelte';
	import { onMount } from 'svelte';

	let id = $page.params.id;
	let scheduleId = $page.params.scheduleId;

	const { form, handleSubmit } = createForm({
		initialValues: {
			task: {} as ScheduleTask,
			from: '',
			to: '',
			row: {} as Row
		},

		onSubmit: async (values) => {
			values.task.fromDate = new Date(values.from);
			values.task.toDate = new Date(values.to);
			values.task.resourceId = values.row.id;
			const [createdSuccess, createdError] = await createTask(scheduleId, values.task);
			if (createdError) {
				error(createdError);
				goto(`/project/schedule/${id}`);
			} else {
				success(createdSuccess);
				goto(`/project/schedule/${id}`);
			}
		}
	});

	onMount(async () => {
		const [createdSuccess, createdError] = await getRows(scheduleId);
		if (createdError) {
			error(createdError);
			goto(`/project/schedule/${id}`);
		} else {
			if (createdSuccess.length === 0) {
				error('Schedule was no rows. Create row before adding tasks.');
				goto(`/project/schedule/${id}`);
			}
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`/project/schedule/${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Create new task</h2>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Label<span class="text-red-600">*</span>
			</p>
			<TextInput placeholder="Name" bind:value={$form.task.label} />
		</div>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Row<span class="text-red-600">*</span>
			</p>
			<RowSelectInput bind:value={$form.row} projectId={id} />
		</div>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				From<span class="text-red-600">*</span>
			</p>
			<DateTimeInput title={'enter a start date for task'} bind:value={$form.from} />
		</div>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				To<span class="text-red-600">*</span>
			</p>
			<DateTimeInput title={'enter a end date for task'} bind:value={$form.to} />
		</div>
	</div>
	<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => goto(`/project/schedule/${id}`)} />
	</div>
</ModalWindow>
