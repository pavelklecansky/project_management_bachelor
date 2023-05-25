<script lang="ts">
	import {goto, params} from "@roxi/routify";
	import type {BudgetCategory} from '../../../../../types/core.type';
	import {createForm} from 'svelte-forms-lib';
	import {error, success} from '../../../../../lib/notification';
	import TextInput from "../../../../../components/core/TextInput.svelte";
	import SubmitButton from "../../../../../components/core/SubmitButton.svelte";
	import CloseButton from "../../../../../components/core/CloseButton.svelte";
	import ModalWindow from '../../../../../components/modal/ModalWindow.svelte';
	import {createBudgetCategory} from '../../../../../lib/budget.service';
	import NumberInput from "../../../../../components/core/NumberInput.svelte";

	let id = $params.id;
	let budgetId = $params.budgetId;

	const { form, handleSubmit } = createForm({
		initialValues: {
			category: {
			} as BudgetCategory,
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await createBudgetCategory(
					budgetId,
					values.category
			);
			if (createdError) {
				error(createdError);
				$goto(`/project/budget/${id}`);
			} else {
				success(createdSuccess);
				$goto(`/project/budget/${id}`);
			}
		},
	});
</script>

<ModalWindow outsideClickGotoPath={`/project/budget/${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Create new budget category</h2>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Label
			</p>
			<TextInput placeholder="Name" bind:value={$form.category.label} />
		</div>
		<div class="w-full mt-4 ">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Budget
			</p>
			<NumberInput bind:value={$form.category.budget} />
		</div>
	</div>
	<div
		class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
	>
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => $goto(`/project/budget/${id}`)} />
	</div>
</ModalWindow>
