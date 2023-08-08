<script lang="ts">
    import { page } from '$app/stores';
    import {onMount} from 'svelte';
    import type {Budget} from '$lib/types/core.type';
    import CreateButton from "$lib/components/core/CreateButton.svelte";
    import {getBudgetByProject} from '$lib/budget.service';
    import BudgetCategory from "$lib/components/budget/BudgetCategory.svelte";

    let budget = {} as Budget;
    let loaded = false;
    let id = $page.params.id;

    onMount(async () => {
        const [success, errorMessage] = await getBudgetByProject(id);
        budget = success!;
        loaded = true;
    });
</script>

<div class="flex justify-between m-2">
    <h1 class="text-2xl">Budget</h1>
    <a href={`/project/${id}/budget/${budget.id}/create-category`}>
        <CreateButton text={"Create new budget category"}/>
    </a>
</div>

{#if loaded}
    <div>
        {#each budget.budgetCategories as budgetCategory}
            <BudgetCategory {id} {budgetCategory}/>
        {/each}
    </div>
{/if}