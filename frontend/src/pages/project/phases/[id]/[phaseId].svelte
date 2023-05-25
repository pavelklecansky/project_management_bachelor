<script lang="ts">
    import {goto, params, url} from '@roxi/routify';
    import {onMount} from 'svelte';
    import {getAllTaskOfPhase, getPhase} from '../../../../lib/phase.service';
    import type {Phase, Task} from '../../../../types/core.type';
    import EditButton from "../../../../components/core/EditButton.svelte";
    import DeleteButton from "../../../../components/core/DeleteButton.svelte";
    import {error} from '../../../../lib/notification';
    import TaskList from "../../../../components/task/TaskList.svelte";

    let phase = {} as Phase;
    let tasks = [] as Task[];
    let loaded = false;


    onMount(async () => {
        let id = $params.phaseId;
        const [success, errorMessage] = await getPhase(id);
        if (!success || errorMessage) {
            error(errorMessage);
            $goto(`./../`);
        } else {
            phase = success!;
            loaded = true;
        }
    });
</script>

<div>
    {#if loaded}
        <div>
            <h1 class="text-4xl font-bold">{phase.name}</h1>
            <div class="flex justify-between gap-4">
                <div class="w-4/5">
                    <div class="mt-2">
                        <h2 class="text-xl">Description</h2>
                        <p>
                            {phase.note}
                        </p>
                    </div>

                    <div class="mt-2">
                        <hr/>
                        <p class="mt-2">
                            <span class="font-bold">Start date:</span>
                            {new Date(phase.startDate).toLocaleDateString("cs")}
                        </p>
                        <p>
                            <span class="font-bold">End date:</span>
                            {new Date(phase.endDate).toLocaleDateString("cs")}
                        </p>
                    </div>
                </div>
                <div class="w-1/5 flex flex-col text-center">
                    <h3 class="text-1xl font-bold">Actions</h3>
                    <a class="mt-2" href={$url(`./delete/${phase.id}`)}
                    >
                        <DeleteButton text={"Delete phase"} full={true}/>
                    </a
                    >
                    <a class="mt-2" href={$url(`./edit/${phase.id}`)}
                    >
                        <EditButton text={"Edit phase"} full={true}/>
                    </a
                    >
                </div>
            </div>
        </div>
        <div class="mt-2">
            <hr/>
            <h2 class="text-3xl font-bold">Tasks</h2>
            <div class="mt-2">
                {#await getAllTaskOfPhase(phase.id) then [tasks, errorMessage]}
                    <TaskList {tasks} allTasks={true}/>
                {/await}
            </div>
        </div>
    {/if}
</div>