<script lang="ts">
    import {goto} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import SubmitButton from "../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../components/core/CloseButton.svelte";
    import TextInput from "../../../components/core/TextInput.svelte";
    import {createForm} from "svelte-forms-lib";
    import type {Project} from "../../../types/core.type";
    import TextArea from "../../../components/core/TextArea.svelte";
    import DateInput from "../../../components/core/DateInput.svelte";
    import {createProject} from "../../../lib/project.service";
    import {load} from "../../../lib/projects.store";
    import ModalWindow from "../../../components/modal/ModalWindow.svelte";

    const { form, handleSubmit } = createForm({
		initialValues: {
			project: {} as Project,
			startDate: "",
			endDate: "",
		},

		onSubmit: async (values) => {
			values.project.startDate = new Date(values.startDate);
			values.project.endDate = new Date(values.endDate);
			const [createdSuccess, createdError] = await createProject(
				values.project
			);
			if (createdError) {
				error(createdError);
				$goto("./../");
			} else {
				success(createdSuccess);
				load();
				$goto("./../");
			}
		},
	});
</script>

<ModalWindow outsideClickGotoPath={"./../"} {handleSubmit}>
	<div class="px-4 mb-4 on">
		<h2 class="text-3xl font-medium">Create new project</h2>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Name<span class="text-red-600">*</span>
			</p>
			<TextInput placeholder="Name" bind:value={$form.project.name} />
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Description<span class="text-red-600">*</span>
			</p>
			<TextArea
				placeholder="Description"
				bind:value={$form.project.description}
			/>
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Start date<span class="text-red-600">*</span>
			</p>
			<DateInput title={"enter a start date for project"} bind:value={$form.startDate} />
		</div>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				End date<span class="text-red-600">*</span>
			</p>
			<DateInput title={"enter a end date for project"} bind:value={$form.endDate} />
		</div>
	</div>
	<div
		class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
	>
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => $goto("./../")} />
	</div>
</ModalWindow>
