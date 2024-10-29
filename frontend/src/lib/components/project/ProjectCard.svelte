<script lang="ts">
	import type { Project } from '$lib/types/core.type';

	interface Props {
		project?: any;
	}

	let { project = {} as Project }: Props = $props();
	let hidden = true;
</script>

<div class="md:w-1/4 w-full max-w-sm px-4 py-3 bg-white rounded-md shadow-md flex-grow box-border">
	<div class="flex items-center justify-between">
		<span class="text-sm font-light text-gray-800">Project</span>
	</div>

	<div>
		<a href={`/project/${project.id}`}
			><h1 class="mt-2 text-lg font-semibold text-gray-800">
				{project.name}
			</h1>
		</a>
		<p class="mt-2 text-sm text-gray-600">
			{project.description}
		</p>
	</div>
</div>
