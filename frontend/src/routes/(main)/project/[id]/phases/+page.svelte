<script lang="ts">
	import { page } from '$app/stores';
	import { onMount } from 'svelte';
	import { getAllPhasesOfProject } from '$lib/phase.service';
	import type { Phase } from '$lib/types/core.type';
	import CreateButton from '$lib/components/core/CreateButton.svelte';

	let phases = $state([] as Phase[]);

	onMount(async () => {
		let id = $page.params.id;
		const [success, errorMessage] = await getAllPhasesOfProject(id);
		phases = success!;
	});
</script>

<div class="flex justify-between m-2">
	<h1 class="text-2xl">Phases</h1>
	<a href={`/project/${$page.params.id}/phases/create`}>
		<CreateButton text={'Create new phase'} />
	</a>
</div>

<div class="flex md:justify-start justify-center flex-wrap gap-5">
	{#each phases as phase}
		<div
			class="md:w-1/4 w-full max-w-sm px-4 py-3 bg-white rounded-md shadow-md flex-grow box-border"
		>
			<div class="flex items-center justify-between">
				<span class="text-sm font-light text-gray-800">Phase</span>
			</div>

			<div>
				<a href={`/project/${$page.params.id}/phases/${phase.id}`}
					><h1 class="mt-2 text-lg font-semibold text-gray-800">
						{phase.name}
					</h1>
				</a>
				<p class="mt-2 text-sm text-gray-600">
					{phase.note} - Start Date: {new Date(phase.startDate).toLocaleDateString('cs')} | End date:
					{new Date(phase.endDate).toLocaleDateString('cs')}
				</p>
			</div>
		</div>
	{/each}
</div>
