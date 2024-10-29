<script>
	import { preventDefault } from 'svelte/legacy';

	import { createForm } from 'svelte-forms-lib';
	import { error, success } from '$lib/notification';
	import { renameFile } from '$lib/storage.service';
	import { getContext } from 'svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';

	/** @type {{oldName: any, id: any, queryParams: any}} */
	let { oldName, id, queryParams } = $props();

	const { close } = getContext('simple-modal');

	const { form, handleSubmit } = createForm({
		initialValues: {
			id: id,
			oldName: oldName,
			newName: oldName,
			queryParams
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await renameFile(
				values.id,
				values.oldName,
				values.newName,
				values.queryParams
			);
			if (createdError) {
				error(createdError);
				close();
			} else {
				success(createdSuccess);
				close();
			}
		}
	});
</script>

<form onsubmit={preventDefault(handleSubmit)}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Rename file</h2>
		<p>Name: {$form.oldName}</p>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">New name</p>
			<TextInput placeholder="Name" bind:value={$form.newName} />
		</div>
		<div class="flex mt-6 justify-between py-4 px-4 border-t border-gray-300 false">
			<SubmitButton text="Edit" />
			<CloseButton text="Close" on:click={() => close()} />
		</div>
	</div>
</form>
