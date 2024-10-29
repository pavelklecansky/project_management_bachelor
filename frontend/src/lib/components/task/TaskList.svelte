<script lang="ts">
	import { run } from 'svelte/legacy';

	import type { Task } from '$lib/types/core.type';
	import TaskCard from './TaskCard.svelte';
	interface Props {
		projectId: string;
		allTasks?: boolean;
		tasks?: Task[];
	}

	let { projectId, allTasks = false, tasks = $bindable([]) }: Props = $props();

	run(() => {
		if (!allTasks) {
			tasks = tasks.slice(0, 6);
		}
	});
</script>

<div class="flex md:justify-start justify-center flex-wrap gap-5">
	{#each tasks as task}
		<TaskCard {projectId} {task} />
	{/each}
</div>
