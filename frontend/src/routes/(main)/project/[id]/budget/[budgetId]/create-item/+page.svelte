<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import type { BudgetCategory, BudgetItem } from '$lib/types/core.type';
	import { createForm } from 'svelte-forms-lib';
	import { error, success } from '$lib/notification';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import { createBudgetItem, getCategory } from '$lib/budget.service';
	import NumberInput from '$lib/components/core/NumberInput.svelte';
	import { onMount } from 'svelte';
	import { getItemsSum } from '$lib/utils';

	let id = $page.params.id;
	let categoryId = $page.params.budgetId;

	let category: BudgetCategory;

	function isMoreThenBudget(item: BudgetItem) {
		const itemsSum = getItemsSum(category.items) + item.budget;
		return itemsSum > category.budget;
	}

	const { form, handleSubmit } = createForm({
		initialValues: {
			item: {} as BudgetItem
		},

		onSubmit: async (values) => {
			if (isMoreThenBudget(values.item)) {
				error('Item budget bigger then category budget.');
			} else {
				const [createdSuccess, createdError] = await createBudgetItem(categoryId, values.item);
				if (createdError) {
					error(createdError);
					await goto(`/project/${id}/budget`);
				} else {
					success(createdSuccess);
					await goto(`/project/${id}/budget`);
				}
			}
		}
	});

	onMount(async () => {
		const [success, errorMessage] = await getCategory(categoryId);
		if (!success || errorMessage) {
			error(errorMessage);
			await goto(`/project/${id}/budget`);
		} else {
			category = success!;
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`/project/${id}/budget`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Create new budget item</h2>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Label</p>
			<TextInput placeholder="Name" bind:value={$form.item.label} />
		</div>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Budget</p>
			<NumberInput bind:value={$form.item.budget} />
		</div>
	</div>
	<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => goto(`/project/${id}/budget`)} />
	</div>
</ModalWindow>
