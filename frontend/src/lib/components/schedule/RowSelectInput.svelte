<script lang="ts">
	import { run } from 'svelte/legacy';

	import { onMount } from 'svelte';
	import Select from 'svelte-select';
	import { isEmptyObjectOrNull } from '$lib/utils';
	import type { Row } from '$lib/types/core.type';
	import { getScheduleByProject } from '$lib/schedule.service';

	interface Props {
		value?: any;
		projectId: any;
		rowId?: any;
	}

	let { value = $bindable({} as Row), projectId, rowId = null }: Props = $props();

	let rows = $state([] as Row[]);
	let items = $state([]);
	let defaultValue = $state({
		value: '',
		label: ''
	});
	let loaded = $state(false);

	onMount(async () => {
		const [successResponse, errorResponse] = await getScheduleByProject(projectId);
		rows = successResponse.rows;
		if (rowId) {
			value = rows.find((element) => element.id === rowId);
		}
		loaded = true;
	});

	function handleSelect(event) {
		const rowId = event.detail.value;
		value = rows.find((element) => element.realId === rowId);
	}

	function handleClear() {
		value = {} as Row;
	}

	run(() => {
		if (loaded) {
			items = rows.map((row) => {
				return {
					value: row.realId,
					label: row.label
				};
			});
		}
		if (!isEmptyObjectOrNull(value)) {
			defaultValue = {
				value: value.realId || '',
				label: value.label || ''
			};
		}
	});
</script>

{#if loaded}
	<Select {items} value={defaultValue} on:select={handleSelect} on:clear={handleClear} />
{/if}
