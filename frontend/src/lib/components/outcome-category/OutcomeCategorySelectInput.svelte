<script lang="ts">
	import { run } from 'svelte/legacy';

	import { onMount } from 'svelte';
	import Select from 'svelte-select';
	import { isEmptyObjectOrNull } from '$lib/utils';
	import type { OutcomeCategory } from '$lib/types/core.type';
	import { getOutcomeCategoriesOfProject } from '$lib/outcome-categories.service';

	interface Props {
		value?: any;
		projectId: any;
	}

	let { value = $bindable({} as OutcomeCategory), projectId }: Props = $props();

	let outcomeCategories = $state([] as OutcomeCategory[]);
	let items = $state([]);
	let defaultValue = $state({
		value: '',
		label: ''
	});
	let loaded = $state(false);

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

	run(() => {
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
	});
</script>

{#if loaded}
	<Select {items} value={defaultValue} on:select={handleSelect} on:clear={handleClear} />
{/if}
