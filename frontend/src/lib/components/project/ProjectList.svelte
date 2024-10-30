<script lang="ts">
	import ProjectCard from './ProjectCard.svelte';
	import type { Project } from '$lib/types/core.type';

	interface Props {
		projects: Project[];
	}

	let { projects }: Props = $props();
</script>

<div class="flex md:justify-start justify-center flex-wrap gap-5">
	{#each projects as project}
		<ProjectCard {project} />
	{/each}
</div>
