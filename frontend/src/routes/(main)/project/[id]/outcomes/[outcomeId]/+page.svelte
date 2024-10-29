<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	import type { Outcome } from '$lib/types/core.type';
	import EditButton from '$lib/components/core/EditButton.svelte';
	import DeleteButton from '$lib/components/core/DeleteButton.svelte';
	import { error } from '$lib/notification';
	import { getOutcome } from '$lib/outcomes.service';
	import CreateButton from '$lib/components/core/CreateButton.svelte';

	let outcome = {} as Outcome;
	let loaded = false;

	onMount(async () => {
		let id = $page.params.outcomeId;
		const [success, errorMessage] = await getOutcome(id);
		if (!success || errorMessage) {
			error(errorMessage);
			await goto(`./../`);
		} else {
			outcome = success!;
			loaded = true;
		}
	});
</script>

<div>
	{#if loaded}
		<div>
			<h1 class="text-4xl font-bold">{outcome.name}</h1>
			<div class="flex justify-between gap-4">
				<div class="w-4/5">
					<div class="mt-2">
						<h2 class="text-xl">Description</h2>
						<p>
							{outcome.description}
						</p>
					</div>
					<div class="mt-2">
						<h2 class="text-xl">Phase</h2>
						<p>
							{outcome.phase.name}
						</p>
						<p>
							<span class="font-bold">Phase Start date:</span>
							{new Date(outcome.phase.startDate).toLocaleDateString('cs')}
						</p>
						<p>
							<span class="font-bold">Phase End date:</span>
							{new Date(outcome.phase.endDate).toLocaleDateString('cs')}
						</p>
					</div>
					{#if outcome.outcomeCategory}
						<div class="mt-2">
							<h2 class="text-xl">Outcome category</h2>
							<p>
								{outcome.outcomeCategory.name}
							</p>
						</div>
					{/if}
					<div class="mt-2">
						<hr />
						<p class="mt-2">
							<span class="font-bold">Start date:</span>
							{new Date(outcome.startDate).toLocaleDateString('cs')}
						</p>
						<p>
							<span class="font-bold">End date:</span>
							{new Date(outcome.endDate).toLocaleDateString('cs')}
						</p>
					</div>
				</div>
				<div class="w-1/5 flex flex-col text-center">
					<h3 class="text-1xl font-bold">Actions</h3>
					<a class="mt-2" href={`./delete/${outcome.id}`}>
						<DeleteButton text={'Delete outcome'} full={true} />
					</a>
					<a class="mt-2" href={`./edit/${outcome.id}`}>
						<EditButton text={'Edit outcome'} full={true} />
					</a>
					<a class="mt-2" href={`./create-result/${outcome.id}`}>
						<CreateButton text={'New result'} full={true} />
					</a>
				</div>
			</div>
			<h2 class="text-3xl mt-4 font-bold">Results</h2>
			<div class="flex md:justify-start justify-center flex-wrap gap-5">
				{#each outcome.results as result (result.id)}
					<div
						class="md:w-1/4 w-full max-w-sm px-4 py-3 bg-white rounded-md shadow-md flex-grow box-border"
					>
						<div class="flex items-center justify-between">
							<span class="text-sm font-light text-gray-800">Result</span>
						</div>

						<div>
							<a href={`./${outcome.id}/result/${result.id}`}
								><h1 class="mt-2 text-lg font-semibold text-gray-800">
									{result.name}
								</h1>
							</a>
							<p class="mt-2 text-sm text-gray-600">
								{result.description}
							</p>
						</div>
					</div>
				{/each}
			</div>
		</div>
	{/if}
</div>
