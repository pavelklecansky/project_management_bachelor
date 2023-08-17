<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';

	import { error, success } from '$lib/notification';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import { createForm } from 'svelte-forms-lib';
	import TextArea from '$lib/components/core/TextArea.svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import type { Result } from '$lib/types/core.type';
	import { getResult, updateResult } from '$lib/results.service';
	import { onMount } from 'svelte';

	let id = $page.params.resultId;

	const { form, handleSubmit } = createForm({
		initialValues: {
			result: {} as Result
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await updateResult(values.result);
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
		const [success, errorMessage] = await getResult(id);
		if (!success || errorMessage) {
			error(errorMessage);
			goto(`./../${id}`);
		} else {
			$form.result = success!;
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`./../${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Edit result</h2>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Name</p>
			<TextInput placeholder="Name" bind:value={$form.result.name} />
		</div>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Description</p>
			<TextArea placeholder="Description" bind:value={$form.result.description} />
		</div>
	</div>
	<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
		<SubmitButton text="Edit" />
		<CloseButton text="Close" on:click={() => goto(`./../${id}`)} />
	</div>
</ModalWindow>
