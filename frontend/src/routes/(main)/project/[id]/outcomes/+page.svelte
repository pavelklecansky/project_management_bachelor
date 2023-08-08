<script lang="ts">
    import { page } from '$app/stores';
    import {onMount} from 'svelte';
    import type {Outcome, OutcomeCategory} from '$lib/types/core.type';
    import CreateButton from "$lib/components/core/CreateButton.svelte";
    import {getOutcomesOfProject} from '$lib/outcomes.service';
    import {getOutcomeCategoriesOfProject} from '$lib/outcome-categories.service';

    let outcomes = [] as Outcome[];
    let outcomeCategories = [] as OutcomeCategory[];

    onMount(async () => {
        let id = $page.params.id;
        const [successOutcome, errorMessageOutcome] = await getOutcomesOfProject(id);
        outcomes = successOutcome;
        const [successOutcomeCategory, errorMessageOutcomeCategory] = await getOutcomeCategoriesOfProject(id);
        outcomeCategories = successOutcomeCategory;
    });
</script>

<div class="flex justify-end w-full mb-2">
    <a class="mr-4" href={`./create/${$page.params.id}`}>
        <CreateButton text={"Create new outcome"}/>
    </a>

    <a class="mr-4" href={`./create-category/${$page.params.id}`}>
        <CreateButton text={"Create new outcome category"}/>
    </a>
</div>

<h1 class="text-2xl">Outcomes</h1>

<div class="flex md:justify-start justify-center flex-wrap gap-5">
    {#each outcomes as outcome (outcome.id)}
        <div class="md:w-1/4 w-full max-w-sm px-4 py-3 bg-white rounded-md shadow-md flex-grow box-border">
            <div class="flex items-center justify-between">
                <span class="text-sm font-light text-gray-800 ">Outcome</span>
            </div>

            <div>
                <a href={`./${$page.params.id}/${outcome.id}`}
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

<h1 class="text-2xl mt-4">Outcome categories</h1>

<div class="flex md:justify-start justify-center flex-wrap gap-5">
    {#each outcomeCategories as outcomeCategory (outcomeCategory.id)}
        <div class="md:w-1/4 w-full max-w-sm px-4 py-3 bg-white rounded-md shadow-md flex-grow box-border">
            <div class="flex items-center justify-between">
                <span class="text-sm font-light text-gray-800 ">Outcome category</span>
            </div>

            <div>
                <a href={`./${$page.params.id}/category/${outcomeCategory.id}`}
                ><h1 class="mt-2 text-lg font-semibold text-gray-800">
                    {outcomeCategory.name}
                </h1>
                </a>
                <p class="mt-2 text-sm text-gray-600">
                    {outcomeCategory.description}
                </p>
            </div>
        </div>
    {/each}
</div>