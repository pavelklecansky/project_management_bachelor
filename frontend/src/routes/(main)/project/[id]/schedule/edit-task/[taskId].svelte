<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import type { Row, ScheduleTask } from '$lib/types/core.type';
	import { createForm } from 'svelte-forms-lib';
	import { getTask, updateTask } from '$lib/schedule.service';
	import { error, success } from '$lib/notification';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import DateTimeInput from '$lib/components/core/DateTimeInput.svelte';
	import RowSelectInput from '$lib/components/schedule/RowSelectInput.svelte';
	import { onMount } from 'svelte';
	import { dateISOFormatFull } from '$lib/utils';

	let id = $page.params.id;
	let loaded = false;

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
			const [createdSuccess, createdError] = await updateTask(values.task.realId, values.task);
			if (createdError) {
				error(createdError);
				await goto(`/project/schedule/${id}`);
			} else {
				success(createdSuccess);
				await goto(`/project/schedule/${id}`);
			}
		}
	});

	onMount(async () => {
		const [success, errorMessage] = await getTask($page.params.taskId);
		if (!success || errorMessage) {
			error(errorMessage);
			goto(`./../`);
		} else {
			$form.task = success!;
			$form.from = dateISOFormatFull(success.fromDate);
			$form.to = dateISOFormatFull(success.toDate);
			loaded = true;
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`/project/schedule/${id}`} {handleSubmit}>
	{#if loaded}
		<div class="px-4 mb-4">
			<h2 class="text-3xl font-medium">Update task</h2>
			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Label</p>
				<TextInput placeholder="Name" bind:value={$form.task.label} />
			</div>
			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Row</p>
				<RowSelectInput bind:value={$form.row} projectId={id} rowId={$form.task.resourceId} />
			</div>
			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">From</p>
				<DateTimeInput bind:value={$form.from} />
			</div>
			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">To</p>
				<DateTimeInput bind:value={$form.to} />
			</div>
		</div>
		<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
			<SubmitButton text="Edit" />
			<CloseButton text="Close" on:click={() => goto(`/project/schedule/${id}`)} />
		</div>
	{/if}
</ModalWindow>
