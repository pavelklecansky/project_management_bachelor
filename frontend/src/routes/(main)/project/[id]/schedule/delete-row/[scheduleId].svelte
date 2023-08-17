<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import type { Row } from '$lib/types/core.type';
	import { createForm } from 'svelte-forms-lib';
	import { deleteRow } from '$lib/schedule.service';
	import { error, success } from '$lib/notification';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import RowSelectInput from '$lib/components/schedule/RowSelectInput.svelte';

	let id = $page.params.id;
	let scheduleId = $page.params.scheduleId;

	const { form, handleSubmit } = createForm({
		initialValues: {
			row: {} as Row
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await deleteRow(values.row.realId);
			if (createdError) {
				error(createdError);
				goto(`/project/schedule/${id}`);
			} else {
				success(createdSuccess);
				goto(`/project/schedule/${id}`);
			}
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`/project/schedule/${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Delete row</h2>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Row</p>
			<RowSelectInput bind:value={$form.row} projectId={id} />
		</div>
	</div>
	<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
		<SubmitButton color="red" text="Delete" />
		<CloseButton text="Close" on:click={() => goto(`/project/schedule/${id}`)} />
	</div>
</ModalWindow>
