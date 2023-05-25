<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import SubmitButton from "../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../components/core/CloseButton.svelte";
    import TextInput from "../../../components/core/TextInput.svelte";
    import {createForm} from "svelte-forms-lib";
    import TextArea from "../../../components/core/TextArea.svelte";
    import DateInput from "../../../components/core/DateInput.svelte";
    import {createTask} from "../../../lib/task.service";
    import {load} from "../../../lib/projects.store";
    import Select from "svelte-select";
    import type {Task} from "../../../types/core.type";
    import NumberInput from "../../../components/core/NumberInput.svelte";
    import {getAllPriorityTypes, getAllStatusTypes, getDefaultPriorityType, getDefaultStatusType, isEmptyObject, transferify,} from "../../../lib/utils";
    import UserSelectInput from "../../../components/user/UserSelectInput.svelte";
    import ModalWindow from "../../../components/modal/ModalWindow.svelte";
    import GroupSelectInput from "../../../components/group/GroupSelectInput.svelte";
    import PhaseSelectInput from "../../../components/phase/PhaseSelectInput.svelte";

    let id = $params.id;

	function handlePrioritySelect(event) {
		$form.task.priority = event.detail.value;
	}

	function handleStatusSelect(event) {
		$form.task.status = event.detail.value;
	}

	const { form, handleSubmit } = createForm({
		initialValues: {
			task: {
				priority: "NORMAL",
				status: "NEW",
			} as Task,
			startDate: "",
			endDate: "",
		},

		onSubmit: async (values) => {
			values.task.startDate = new Date(values.startDate);
			values.task.endDate = new Date(values.endDate);
			values.task.project = $params.id;
			values.task.assigned = isEmptyObject(values.task.assigned)
				? null
				: values.task.assigned;
			values.task.assignedForGroup = isEmptyObject(values.task.assignedForGroup)
				? null
				: values.task.assignedForGroup;
			values.task.phase = isEmptyObject(values.task.phase)
					? null
					: values.task.phase;
			const [createdSuccess, createdError] = await createTask(
				transferify(values.task)
			);
			if (createdError) {
				error(createdError);
				$goto(`./../${id}`);
			} else {
				success(createdSuccess);
				load();
				$goto(`./../${id}`);
			}
		},
	});
</script>

<ModalWindow outsideClickGotoPath={`./../${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Create new task</h2>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Name<span class="text-red-600">*</span>
			</p>
			<TextInput placeholder="Name" bind:value={$form.task.name} />
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Description<span class="text-red-600">*</span>
			</p>
			<TextArea
				placeholder="Description"
				bind:value={$form.task.description}
			/>
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Status<span class="text-red-600">*</span>
			</p>

			<Select
				items={getAllStatusTypes()}
				value={getDefaultStatusType()}
				on:select={handleStatusSelect}

			/>
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Assigneed
			</p>

			<UserSelectInput
				bind:value={$form.task.assigned}
				disable={!isEmptyObject($form.task.assignedForGroup)}
			/>
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Assigneed to Group
			</p>

			<GroupSelectInput
				bind:value={$form.task.assignedForGroup}
				disable={!isEmptyObject($form.task.assigned)}
			/>
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Phase
			</p>

			<PhaseSelectInput
					bind:value={$form.task.phase}
					projectId={id}
			/>
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Priority<span class="text-red-600">*</span>
			</p>

			<Select
				items={getAllPriorityTypes()}
				value={getDefaultPriorityType()}
				on:select={handlePrioritySelect}
			/>
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Progress<span class="text-red-600">*</span>
			</p>
			<NumberInput bind:value={$form.task.progress} max="100" />
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Start date<span class="text-red-600">*</span>
			</p>
			<DateInput title={"enter a start date for task, the start cannot be before the start of the project"} bind:value={$form.startDate} />
		</div>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				End date<span class="text-red-600">*</span>
			</p>
			<DateInput title={"enter a end date for task"} bind:value={$form.endDate} />
		</div>
	</div>
	<div
		class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
	>
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => $goto(`./../${id}`)} />
	</div>
</ModalWindow>
