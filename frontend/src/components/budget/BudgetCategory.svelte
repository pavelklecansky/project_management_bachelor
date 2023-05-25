<script lang="ts">
    import type {BudgetCategory} from '../../types/core.type';
    import BudgetItem from "./BudgetItem.svelte";
    import {EditIcon, Trash2Icon} from "svelte-feather-icons";
    import {url} from "@roxi/routify";
    import CreateButton from "../core/CreateButton.svelte";
    import {getItemsSum} from '../../lib/utils';

    export let budgetCategory: BudgetCategory;
    export let id;

    function calcTotal() {
        const budgetMax = budgetCategory.budget;
        const itemsSum = getItemsSum(budgetCategory.items);
        return budgetMax - itemsSum;
    }
</script>

<div class="border border-gray-200 p-6 rounded-lg">
    <div class="flex justify-between">
        <div>
            <h2 class="text-2xl font-bold">{budgetCategory.label}</h2>
        </div>
        <div class="text-xl font-medium flex items-baseline">
            <span class="text-green-600 mr-2">{budgetCategory.budget},-</span>
            <div class="flex justify-center">
                <a href={$url(`./${id}/edit-category/${budgetCategory.id}`)}
                >
                    <EditIcon size="1x"/>
                </a
                >
                <a href={$url(`./${id}/delete-category/${budgetCategory.id}`)}
                >
                    <Trash2Icon size="1x"/>
                </a
                >
            </div>
        </div>
    </div>
    <div>
        {#each budgetCategory.items as item}
            <BudgetItem {id} {item}/>
        {/each}
        <div class="flex justify-end mt-2 mb-2">
            <a href={$url(`./${id}/create-item/${budgetCategory.id}`)}>
                <CreateButton text="Create new budget item"/>
            </a>
        </div>
        <hr>
        <div class="flex justify-end text-2xl">
            <p><span class="font-medium">Total:</span> <span class="text-green-600 font-semibold">{calcTotal()},-</span></p>
        </div>
    </div>
</div>