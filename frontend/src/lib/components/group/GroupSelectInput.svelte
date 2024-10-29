<script lang="ts">
	import { run } from 'svelte/legacy';

	import { onMount } from 'svelte';
	import Select from 'svelte-select';
	import { getGroups } from '$lib/groups.service';
	import { getDataFromResponse, isEmptyObjectOrNull } from '$lib/utils';
	import type { Group } from '$lib/types/core.type';

	interface Props {
		value?: any;
		disable?: boolean;
	}

	let { value = $bindable({} as Group), disable = false }: Props = $props();

	let groups = $state([] as Group[]);
	let loaded = $state(false);

	onMount(async () => {
		const [successResponse, errorResponse] = await getGroups();
		groups = getDataFromResponse(successResponse);
		loaded = true;
	});

	let items = $state([]);
	let defaultValue = $state({
		value: '',
		label: ''
	});

	function handleSelect(event) {
		const groupId = event.detail.value;
		value = groups.find((element) => element.id === groupId);
	}

	function handleClear() {
		value = {} as Group;
	}

	run(() => {
		if (loaded) {
			items = groups.map((group) => {
				return {
					value: group.id,
					label: group.name
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
	<Select
		value={defaultValue}
		{items}
		isDisabled={disable}
		on:select={handleSelect}
		on:clear={handleClear}
	/>
{/if}
