<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import type {Row} from '../../../../../types/core.type';
    import {createForm} from 'svelte-forms-lib';
    import {createRow} from '../../../../../lib/schedule.service';
    import {error, success} from '../../../../../lib/notification';
    import TextInput from "../../../../../components/core/TextInput.svelte";
    import SubmitButton from "../../../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../../../components/core/CloseButton.svelte";
    import ModalWindow from '../../../../../components/modal/ModalWindow.svelte';

    let id = $params.id;
	let scheduleId = $params.scheduleId;

	const { form, handleSubmit } = createForm({
		initialValues: {
			row: {
			} as Row,
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await createRow(
					scheduleId,
					values.row
			);
			if (createdError) {
				error(createdError);
				$goto(`/project/schedule/${id}`);
			} else {
				success(createdSuccess);
				$goto(`/project/schedule/${id}`);
			}
		},
	});
</script>

<ModalWindow outsideClickGotoPath={`/project/schedule/${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Create new row</h2>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Label<span class="text-red-600">*</span>
			</p>
			<TextInput placeholder="Name" bind:value={$form.row.label} />
		</div>
	</div>
	<div
		class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
	>
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => $goto(`/project/schedule/${id}`)} />
	</div>
</ModalWindow>
