<script lang="ts">
	import ProjectList from '$lib/components/project/ProjectList.svelte';
	import TaskList from '$lib/components/task/TaskList.svelte';
	import type { Project, Task } from '$lib/types/core.type.js';

	interface Props {
		data: { projects: Project[]; tasks: Task[] };
	}

	let { data }: Props = $props();
</script>

<div>
	<div>
		<h1 class="text-2xl">My projects</h1>
		<ProjectList projects={data.projects} />
	</div>
	<div class="mt-6">
		<h1 class="text-2xl">My tasks</h1>
		<div class="mt-2">
			<TaskList projectId="" tasks={data.tasks} />
		</div>
	</div>
</div>
