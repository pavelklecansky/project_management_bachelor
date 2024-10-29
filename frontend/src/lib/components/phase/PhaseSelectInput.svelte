<script lang="ts">
	import { run } from 'svelte/legacy';

	import { onMount } from 'svelte';
	import Select from 'svelte-select';
	import { isEmptyObjectOrNull } from '$lib/utils';
	import type { Phase } from '$lib/types/core.type';
	import { getAllPhasesOfProject, getAllPhasesOfProjectByTask } from '$lib/phase.service';

	interface Props {
		value?: any;
		projectId: any;
		taskId: any;
		title?: string;
	}

	let { value = $bindable({} as Phase), projectId, taskId, title = '' }: Props = $props();

	let phases = $state([] as Phase[]);
	let items = $state([]);
	let defaultValue = $state({
		value: '',
		label: ''
	});
	let loaded = $state(false);

	onMount(async () => {
		if (projectId) {
			const [successResponse, errorResponse] = await getAllPhasesOfProject(projectId);
			phases = successResponse;
			loaded = true;
		} else if (taskId) {
			const [successResponse, errorResponse] = await getAllPhasesOfProjectByTask(taskId);
			phases = successResponse;
			loaded = true;
		}
	});

	function handleSelect(event) {
		const phaseId = event.detail.value;
		value = phases.find((element) => element.id === phaseId);
	}

	function handleClear() {
		value = {} as Phase;
	}

	run(() => {
		if (loaded) {
			items = phases.map((phase) => {
				return {
					value: phase.id,
					label: phase.name
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
