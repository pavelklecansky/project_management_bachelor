<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import { createForm } from 'svelte-forms-lib';
	import TextArea from '$lib/components/core/TextArea.svelte';
	import DateInput from '$lib/components/core/DateInput.svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import { dateISOFormat, transferify } from '$lib/utils';
	import { onMount } from 'svelte';
	import { Outcome } from '$lib/types/core.type';
	import { getOutcome, updateOutcome } from '$lib/outcomes.service';
	import PhaseSelectInput from '$lib/components/phase/PhaseSelectInput.svelte';
	import OutcomeCategorySelectInput from '$lib/components/outcome-category/OutcomeCategorySelectInput.svelte';

	let id = $page.params.outcomeId;
	let projectId = $page.params.id;
	let loaded = false;

	const { form, handleSubmit } = createForm({
		initialValues: {
			outcome: {} as Outcome,
			startDate: '',
			endDate: ''
		},

		onSubmit: async (values) => {
			values.outcome.startDate = new Date(values.startDate);
			values.outcome.endDate = new Date(values.endDate);
			const [createdSuccess, createdError] = await updateOutcome(transferify(values.outcome));
			if (createdError) {
				error(createdError);
				goto(`./../${id}`);
			} else {
				success(createdSuccess);
				goto(`./../${id}`);
			}
		}
	});

	onMount(async () => {
		const [success, errorMessage] = await getOutcome(id);
		if (!success || errorMessage) {
			error(errorMessage);
			goto(`./../`);
		} else {
			$form.outcome = success!;
			$form.endDate = dateISOFormat(success.endDate);
			$form.startDate = dateISOFormat(success.startDate);
			loaded = true;
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`./../${id}`} {handleSubmit}>
	{#if loaded}
		<div class="px-4 mb-4">
			<h2 class="text-3xl font-medium">Edit outcome</h2>
			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Name</p>
				<TextInput placeholder="Name" bind:value={$form.outcome.name} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Description</p>
				<TextArea placeholder="Description" bind:value={$form.outcome.description} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Phase</p>
				<PhaseSelectInput {projectId} bind:value={$form.outcome.phase} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Outcome Category</p>
				<OutcomeCategorySelectInput {projectId} bind:value={$form.outcome.outcomeCategory} />
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
			<SubmitButton text="Update" />
			<CloseButton text="Close" on:click={() => goto(`./../${id}`)} />
		</div>
	{/if}
</ModalWindow>
