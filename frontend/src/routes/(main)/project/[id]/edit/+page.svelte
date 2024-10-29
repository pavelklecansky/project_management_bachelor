<script lang="ts">
	import { page } from '$app/stores';
	import { afterNavigate, goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import { createForm } from 'svelte-forms-lib';
	import type { Project } from '$lib/types/core.type';
	import TextArea from '$lib/components/core/TextArea.svelte';
	import DateInput from '$lib/components/core/DateInput.svelte';
	import { getProject, updateProject } from '$lib/project.service';
	import { load } from '$lib/projects.store';
	import { onMount } from 'svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import { dateISOFormat } from '$lib/utils';

	let id = $page.params.id;
	let previousPage: string = $state(`/project/${id}`);

	afterNavigate(({ from }) => {
		previousPage = from?.url.pathname || previousPage;
	});

	const { form, handleSubmit } = createForm({
		initialValues: {
			project: {} as Project,
			startDate: '',
			endDate: ''
		},

		onSubmit: async (values) => {
			values.project.startDate = new Date(values.startDate);
			values.project.endDate = new Date(values.endDate);
			const [createdSuccess, createdError] = await updateProject(values.project);
			if (createdError) {
				error(createdError);
				await goto(previousPage);
			} else {
				success(createdSuccess);
				await load();
				await goto(previousPage);
			}
		}
	});

	onMount(async () => {
		const [success, errorMessage] = await getProject(id);
		if (!success || errorMessage) {
			error(errorMessage);
			await goto(previousPage);
		} else {
			$form.project = success!;
			$form.endDate = dateISOFormat(success.endDate);
			$form.startDate = dateISOFormat(success.startDate);
		}
	});
</script>

<ModalWindow outsideClickGotoPath={previousPage} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Update project</h2>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Name</p>
			<TextInput placeholder="Name" bind:value={$form.project.name} />
		</div>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Description</p>
			<TextArea placeholder="Description" bind:value={$form.project.description} />
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
		<CloseButton text="Close" on:click={() => goto(previousPage)} />
	</div>
</ModalWindow>
