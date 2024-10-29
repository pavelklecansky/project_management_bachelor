<script lang="ts">
	import { run } from 'svelte/legacy';

	import ProjectCard from './ProjectCard.svelte';
	import { projects } from '$lib/projects.store';

	interface Props {
		allProjects?: boolean;
	}

	let { allProjects = false }: Props = $props();

	let data;
	run(() => {
		data = $projects;
	});

	run(() => {
		if (!allProjects) {
			data = data.slice(0, 3);
		}
	});
</script>

<div class="flex md:justify-start justify-center flex-wrap gap-5">
	{#each data as project}
		<ProjectCard {project} />
	{/each}
</div>
