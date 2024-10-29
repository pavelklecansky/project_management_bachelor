<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import type { Row } from '$lib/types/core.type';
	import { createForm } from 'svelte-forms-lib';
	import { updateRow } from '$lib/schedule.service';
	import { error, success } from '$lib/notification';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import RowSelectInput from '$lib/components/schedule/RowSelectInput.svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';

	let id = $page.params.id;
	let scheduleId = $page.params.scheduleId;

	const { form, handleSubmit } = createForm({
		initialValues: {
			row: {} as Row
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await updateRow(values.row.realId, values.row);
			if (createdError) {
				error(createdError);
				await goto(`/project/schedule/${id}`);
			} else {
				success(createdSuccess);
				await goto(`/project/schedule/${id}`);
			}
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`/project/schedule/${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Edit row</h2>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Row</p>
			<RowSelectInput bind:value={$form.row} projectId={id} />
		</div>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Label</p>
			<TextInput placeholder="Name" bind:value={$form.row.label} />
		</div>
	</div>
	<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
		<SubmitButton text="Edit" />
		<CloseButton text="Close" on:click={() => goto(`/project/schedule/${id}`)} />
	</div>
</ModalWindow>
