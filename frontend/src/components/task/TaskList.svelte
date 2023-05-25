<script lang="ts">
    import type {Task} from "../../types/core.type";
    import TaskCard from "./TaskCard.svelte";

    export let allTasks = false;
    export let tasks: Task[] = [];

    $: if (!allTasks) {
        tasks = tasks.slice(0, 6);
    }
</script>

<div class="flex md:justify-start justify-center flex-wrap gap-5">
    {#each tasks as task}
        <TaskCard {task} />
    {/each}
</div>
