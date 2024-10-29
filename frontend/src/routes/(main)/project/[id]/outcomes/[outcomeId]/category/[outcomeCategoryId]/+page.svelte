<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	import EditButton from '$lib/components/core/EditButton.svelte';
	import DeleteButton from '$lib/components/core/DeleteButton.svelte';
	import { error } from '$lib/notification';
	import { getOutcomeCategory } from '$lib/outcome-categories.service';
	import type { Outcome, OutcomeCategory } from '$lib/types/core.type';
	import { getOutcomesOfCategory } from '$lib/outcomes.service';

	let outcomeCategory = $state({} as OutcomeCategory);
	let outcomesOfCategory = $state([] as Outcome[]);
	let loaded = $state(false);

	onMount(async () => {
		let id = $page.params.outcomeCategoryId;
		const [success, errorMessage] = await getOutcomeCategory(id);
		if (!success || errorMessage) {
			error(errorMessage);
			goto(`./../../`);
		} else {
			outcomeCategory = success!;
			const [successOutcomes, errorMessage] = await getOutcomesOfCategory(id);
			outcomesOfCategory = successOutcomes!;
			loaded = true;
		}
	});
</script>

<div>
	{#if loaded}
		<div>
			<h1 class="text-4xl font-bold">{outcomeCategory.name}</h1>
			<div class="flex justify-between gap-4">
				<div class="w-4/5">
					<div class="mt-2">
						<h2 class="text-xl">Description</h2>
						<p>
							{outcomeCategory.description}
						</p>
					</div>
				</div>
				<div class="w-1/5 flex flex-col text-center">
					<h3 class="text-1xl font-bold">Actions</h3>
					<a class="mt-2" href={`./delete/${outcomeCategory.id}`}>
						<DeleteButton text={'Delete outcome category'} full={true} />
					</a>
					<a class="mt-2" href={`./edit/${outcomeCategory.id}`}>
						<EditButton text={'Edit outcome category'} full={true} />
					</a>
				</div>
			</div>
		</div>
		<h1 class="text-2xl">Outcomes</h1>

		<div class="flex md:justify-start justify-center flex-wrap gap-5">
			{#each outcomesOfCategory as outcome (outcome.id)}
				<div
					class="md:w-1/4 w-full max-w-sm px-4 py-3 bg-white rounded-md shadow-md flex-grow box-border"
				>
					<div class="flex items-center justify-between">
						<span class="text-sm font-light text-gray-800">Outcome</span>
					</div>

					<div>
						<a href={`/project/outcomes/${$page.params.id}/${outcome.id}`}
							><h1 class="mt-2 text-lg font-semibold text-gray-800">
								{outcome.name}
							</h1>
						</a>
						<p class="mt-2 text-sm text-gray-600">
							{outcome.description}
						</p>
					</div>
				</div>
			{/each}
		</div>
	{/if}
</div>
