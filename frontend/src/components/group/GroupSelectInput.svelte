<script lang="ts">
    import {onMount} from "svelte";
    import Select from "svelte-select";
    import {getGroups} from "../../lib/groups.service";
    import {getDataFromResponse, isEmptyObjectOrNull} from "../../lib/utils";
    import type {Group} from "../../types/core.type";

    export let value = {} as Group;
    export let disable = false;

    let groups = [] as Group[];
    let loaded = false;

    onMount(async () => {
        const [successResponse, errorResponse] = await getGroups();
        groups = getDataFromResponse(successResponse);
        loaded = true;
    });

    let items = [];
    let defaultValue = {
        value: "",
        label: "",
    };

    function handleSelect(event) {
        const groupId = event.detail.value;
        value = groups.find((element) => element.id === groupId);
    }

    function handleClear() {
        value = {} as Group;
    }

    $: {
        if (loaded) {
            items = groups.map((group) => {
                return {
                    value: group.id,
                    label: group.name,
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
        value={defaultValue}
        {items}
        isDisabled={disable}
        on:select={handleSelect}
        on:clear={handleClear}
    />
{/if}
