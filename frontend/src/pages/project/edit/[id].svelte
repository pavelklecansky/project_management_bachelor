<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import SubmitButton from "../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../components/core/CloseButton.svelte";
    import TextInput from "../../../components/core/TextInput.svelte";
    import {createForm} from "svelte-forms-lib";
    import type {Project} from "../../../types/core.type";
    import TextArea from "../../../components/core/TextArea.svelte";
    import DateInput from "../../../components/core/DateInput.svelte";
    import {getProject, updateProject} from "../../../lib/project.service";
    import {load} from "../../../lib/projects.store";
    import {onMount} from "svelte";
    import ModalWindow from "../../../components/modal/ModalWindow.svelte";
    import {dateISOFormat} from "../../../lib/utils";

    const { form, handleSubmit } = createForm({
		initialValues: {
			project: {} as Project,
			startDate: "",
			endDate: "",
		},

		onSubmit: async (values) => {
			values.project.startDate = new Date(values.startDate);
			values.project.endDate = new Date(values.endDate);
			const [createdSuccess, createdError] = await updateProject(
				values.project
			);
			if (createdError) {
				error(createdError);
				$goto(`./../${$form.project.id}`);
			} else {
				success(createdSuccess);
				load();
				$goto(`./../${$form.project.id}`);
			}
		},
	});

	onMount(async () => {
		let id = $params.id;
		const [success, errorMessage] = await getProject(id);
		if (!success || errorMessage) {
			error(errorMessage);
			$goto(`./../`);
		} else {
			$form.project = success!;
			$form.endDate = dateISOFormat(success.endDate);
			$form.startDate = dateISOFormat(success.startDate);
			
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`./../${$form.project.id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Update project</h2>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Name
			</p>
			<TextInput placeholder="Name" bind:value={$form.project.name} />
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Description
			</p>
			<TextArea
				placeholder="Description"
				bind:value={$form.project.description}
			/>
		</div>

		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Start date
			</p>
			<DateInput bind:value={$form.startDate} />
		</div>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				End date
			</p>
			<DateInput bind:value={$form.endDate} />
		</div>
	</div>
	<div
		class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
	>
		<SubmitButton text="Edit" />
		<CloseButton
			text="Close"
			on:click={() => $goto(`./../${$form.project.id}`)}
		/>
	</div>
</ModalWindow>
