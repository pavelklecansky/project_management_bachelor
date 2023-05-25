<script lang="ts">
    import {onMount} from "svelte";
    import Select from "svelte-select";
    import {isEmptyObjectOrNull} from "../../lib/utils";
    import type {Phase} from "../../types/core.type";
    import {getAllPhasesOfProject, getAllPhasesOfProjectByTask,} from "../../lib/phase.service";

    export let value = {} as Phase;
    export let projectId;
    export let taskId;
    export let title = "";

    let phases = [] as Phase[];
    let items = [];
    let defaultValue = {
        value: "",
        label: "",
    };
    let loaded = false;

    onMount(async () => {
        if (projectId) {
            const [successResponse, errorResponse] =
                await getAllPhasesOfProject(projectId);
            phases = successResponse;
            loaded = true;
        } else if (taskId) {
            const [successResponse, errorResponse] =
                await getAllPhasesOfProjectByTask(taskId);
            phases = successResponse;
            loaded = true;
        }
    });

    function handleSelect(event) {
        const phaseId = event.detail.value;
        value = phases.find((element) => element.id === phaseId);
    }

    function handleClear() {
        value = {} as Phase;
    }

    $: {
        if (loaded) {
            items = phases.map((phase) => {
                return {
                    value: phase.id,
                    label: phase.name,
                };
            });
        }
        if (!isEmptyObjectOrNull(value)) {
            defaultValue = {
                value: value.id || "",
                label: value.name || "",
            };
        }
    }
</script>

{#if loaded}
    <Select
        {items}
        value={defaultValue}
        on:select={handleSelect}
        on:clear={handleClear}
    />
{/if}
