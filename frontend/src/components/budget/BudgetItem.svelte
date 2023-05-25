<script lang="ts">
    import type {BudgetItem} from '../../types/core.type';
    import {EditIcon, Trash2Icon} from "svelte-feather-icons";
    import {url} from "@roxi/routify";

    export let item: BudgetItem;
    export let id;
</script>

<div class="ml-10">
    <div class="flex justify-between">
        <h2 class="text-lg font-medium">{item.label}</h2>
        <div class="text-base font-medium flex items-center">
            <span class="text-red-600 mr-2">{item.budget},-</span>
            <div class="flex justify-center">
                <a href={$url(`./${id}/edit-item/${item.id}`)}
                >
                    <EditIcon size="1x"/>
                </a
                >
                <a href={$url(`./${id}/delete-item/${item.id}`)}
                >
                    <Trash2Icon size="1x"/>
                </a
                >
            </div>
        </div>
    </div>
</div>



