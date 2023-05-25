<script lang="ts">
    import {onMount} from "svelte";
    import Select from "svelte-select";
    import {isEmptyObjectOrNull} from "../../lib/utils";
    import type {Row} from '../../types/core.type';
    import {getScheduleByProject} from '../../lib/schedule.service';

    export let value = {} as Row;
    export let projectId;
    export let rowId = null;

    let rows = [] as Row[];
    let items = [];
    let defaultValue = {
        value: "",
        label: "",
    };
    let loaded = false;

    onMount(async () => {
        const [successResponse, errorResponse] = await getScheduleByProject(projectId);
        rows = successResponse.rows;
        if (rowId) {
            value = rows.find((element) => element.id === rowId);
        }
        loaded = true;
    });

    function handleSelect(event) {
        const rowId = event.detail.value;
        value = rows.find((element) => element.realId === rowId);
    }

    function handleClear() {
        value = {} as Row;
    }

    $: {
        if (loaded) {
            items = rows.map((row) => {
                return {
                    value: row.realId,
                    label: row.label,
                };
            });
        }
        if (!isEmptyObjectOrNull(value)) {
            defaultValue = {
                value: value.realId || "",
                label: value.label || "",
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