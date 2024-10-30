<script lang="ts">
	import CreateButton from '$lib/components/core/CreateButton.svelte';
	import ProjectList from '$lib/components/project/ProjectList.svelte';
	import type { Project } from '$lib/types/core.type';

	interface Props {
		data: { projects: Project[] };
		children?: import('svelte').Snippet;
	}

	let { data, children }: Props = $props();
</script>

<div>
	<div class="flex justify-between m-2">
		<h1 class="text-2xl">Projects</h1>
		<a href={'/projects/create'}>
			<CreateButton text={'Create new project'} />
		</a>
	</div>

	<ProjectList projects={data.projects} />
</div>

{@render children?.()}
