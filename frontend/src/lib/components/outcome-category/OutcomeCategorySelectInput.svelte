<script lang="ts">
	import { onMount } from 'svelte';
	import Select from 'svelte-select';
	import { isEmptyObjectOrNull } from '$lib/utils';
	import type { OutcomeCategory } from '$lib/types/core.type';
	import { getOutcomeCategoriesOfProject } from '$lib/outcome-categories.service';

	export let value = {} as OutcomeCategory;
	export let projectId;

	let outcomeCategories = [] as OutcomeCategory[];
	let items = [];
	let defaultValue = {
		value: '',
		label: ''
	};
	let loaded = false;

	onMount(async () => {
		const [successResponse, errorResponse] = await getOutcomeCategoriesOfProject(projectId);
		outcomeCategories = successResponse;
		loaded = true;
	});

	function handleSelect(event) {
		const phaseId = event.detail.value;
		value = outcomeCategories.find((element) => element.id === phaseId);
	}

	function handleClear() {
		value = {} as OutcomeCategory;
	}

	$: {
		if (loaded) {
			items = outcomeCategories.map((outcomeCategory) => {
				return {
					value: outcomeCategory.id,
					label: outcomeCategory.name
				};
			});
		}
		if (!isEmptyObjectOrNull(value)) {
			defaultValue = {
				value: value.id || '',
				label: value.name || ''
			};
		}
	}
</script>

{#if loaded}
	<Select {items} value={defaultValue} on:select={handleSelect} on:clear={handleClear} />
{/if}
