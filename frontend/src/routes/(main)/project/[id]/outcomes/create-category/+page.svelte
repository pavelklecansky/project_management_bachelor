<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import { createForm } from 'svelte-forms-lib';
	import TextArea from '$lib/components/core/TextArea.svelte';
	import type { OutcomeCategory } from '$lib/types/core.type';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import { createOutcomeCategory } from '$lib/outcome-categories.service';

	let id = $page.params.id;

	const { form, handleSubmit } = createForm({
		initialValues: {
			outcomeCategory: {} as OutcomeCategory
		},

		onSubmit: async (values) => {
			values.outcomeCategory.project = id;
			const [createdSuccess, createdError] = await createOutcomeCategory(values.outcomeCategory);
			if (createdError) {
				error(createdError);
				await goto(`./../${id}`);
			} else {
				success(createdSuccess);
				await goto(`./../${id}`);
			}
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`./../${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Create new outcome category</h2>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Name<span class="text-red-600">*</span>
			</p>
			<TextInput placeholder="Name" bind:value={$form.outcomeCategory.name} />
		</div>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Description<span class="text-red-600">*</span>
			</p>
			<TextArea placeholder="Description" bind:value={$form.outcomeCategory.description} />
		</div>
	</div>
	<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => goto(`./../${id}`)} />
	</div>
</ModalWindow>
