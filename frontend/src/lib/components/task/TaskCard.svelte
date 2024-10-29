<script lang="ts">
	import type { Task } from '$lib/types/core.type';

	interface Props {
		projectId: string;
		task?: any;
	}

	let { projectId, task = {} as Task }: Props = $props();
</script>

<div class="md:w-1/4 w-full max-w-sm px-4 py-3 bg-white rounded-md shadow-md flex-grow box-border">
	<div class="flex items-center justify-between">
		<span class="text-sm font-light text-gray-800">Task</span>
	</div>

	<div>
		<a href={`/project/${projectId}/task/${task.id}`}
			><h1 class="mt-2 text-lg font-semibold text-gray-800">
				{task.name}
			</h1>
		</a>
		<p class="mt-2 text-sm text-gray-600">
			{task.description}
		</p>
	</div>
</div>
