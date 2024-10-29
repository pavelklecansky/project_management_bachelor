<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import { createForm } from 'svelte-forms-lib';
	import type { Task } from '$lib/types/core.type';
	import TextArea from '$lib/components/core/TextArea.svelte';
	import DateInput from '$lib/components/core/DateInput.svelte';
	import { getTask, updateTask } from '$lib/task.service';
	import { load } from '$lib/projects.store';
	import { onMount } from 'svelte';
	import Select from 'svelte-select';
	import {
		dateISOFormat,
		getAllPriorityTypes,
		getAllStatusTypes,
		getDefaultPriorityType,
		getDefaultStatusType,
		isEmptyObject,
		isEmptyObjectOrNull,
		priorityLabelFromValue,
		statusLabelFromValue,
		transferify
	} from '$lib/utils';
	import NumberInput from '$lib/components/core/NumberInput.svelte';
	import UserSelectInput from '$lib/components/user/UserSelectInput.svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import GroupSelectInput from '$lib/components/group/GroupSelectInput.svelte';
	import PhaseSelectInput from '$lib/components/phase/PhaseSelectInput.svelte';

	let priorityValueSelect = $state(getDefaultPriorityType());
	let statusValueSelect = $state(getDefaultStatusType());
	let loaded = $state(false);

	function handlePrioritySelect(event) {
		$form.task.priority = event.detail.value;
	}

	function handleStatusSelect(event) {
		$form.task.status = event.detail.value;
	}

	const { form, handleSubmit } = createForm({
		initialValues: {
			task: {} as Task,
			startDate: '',
			endDate: ''
		},

		onSubmit: async (values) => {
			values.task.startDate = new Date(values.startDate);
			values.task.endDate = new Date(values.endDate);
			values.task.assigned = isEmptyObject(values.task.assigned) ? null : values.task.assigned;
			values.task.assignedForGroup = isEmptyObject(values.task.assignedForGroup)
				? null
				: values.task.assignedForGroup;
			const [createdSuccess, createdError] = await updateTask(transferify(values.task));
			if (createdError) {
				error(createdError);
				await goto(`./../${$form.task.id}`);
			} else {
				success(createdSuccess);
				await load();
				await goto(`./../${$form.task.id}`);
			}
		}
	});

	onMount(async () => {
		let id = $page.params.id;
		const [success, errorMessage] = await getTask(id);
		if (!success || errorMessage) {
			error(errorMessage);
			await goto(`./../`);
		} else {
			$form.task = success!;
			$form.endDate = dateISOFormat(success.endDate);
			$form.startDate = dateISOFormat(success.startDate);
			priorityValueSelect.value = $form.task.priority;
			priorityValueSelect.label = priorityLabelFromValue(priorityValueSelect.value);
			statusValueSelect.value = $form.task.status;
			statusValueSelect.label = statusLabelFromValue(statusValueSelect.value);
			loaded = true;
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`./../${$form.task.id}`} {handleSubmit}>
	{#if loaded}
		<div class="px-4 mb-4">
			<h2 class="text-3xl font-medium">Update task</h2>
			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Name</p>
				<TextInput placeholder="Name" bind:value={$form.task.name} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Description</p>
				<TextArea placeholder="Description" bind:value={$form.task.description} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Status</p>

				<Select
					items={getAllStatusTypes()}
					value={statusValueSelect}
					on:select={handleStatusSelect}
				/>
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Assigneed</p>
				<UserSelectInput
					bind:value={$form.task.assigned}
					disable={!isEmptyObjectOrNull($form.task.assignedForGroup)}
				/>
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Assigneed to Group</p>

				<GroupSelectInput
					bind:value={$form.task.assignedForGroup}
					disable={!isEmptyObjectOrNull($form.task.assigned)}
				/>
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Phase</p>

				<PhaseSelectInput bind:value={$form.task.phase} taskId={$page.params.id} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Priority</p>

				<Select
					items={getAllPriorityTypes()}
					value={priorityValueSelect}
					on:select={handlePrioritySelect}
				/>
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Progress</p>
				<NumberInput bind:value={$form.task.progress} max="100" />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Start date</p>
				<DateInput bind:value={$form.startDate} />
			</div>
			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">End date</p>
				<DateInput bind:value={$form.endDate} />
			</div>
		</div>
		<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
			<SubmitButton text="Edit" />
			<CloseButton text="Close" on:click={() => goto(`./../${$form.task.id}`)} />
		</div>
	{/if}
</ModalWindow>
