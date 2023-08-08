<script lang="ts">
    import {onMount} from "svelte";
    import Select from "svelte-select";
    import {getAllOrganizations} from "$lib/organization.service";
    import {getDataFromResponse} from "$lib/utils";
    import type {User} from "$lib/types/authentication.type";
    import type {Organization} from '$lib/types/core.type';

    export let value = {} as User;

    let organizations = [];
    let items = [];
    let defaultValue = [];

    function handleSelect(event) {
        const userOrganizations = event?.detail?.map((item) => {
            const organizationId = item.value;
            return organizations.find(
                (element) => element.id === organizationId
            );
        });
        value.organizations = userOrganizations || [];
    }

    function handleClear() {
        value.organizations = [] as Organization[];
    }

    $: {
        if (organizations) {
            items = organizations.map((organizations) => {
                return {
                    value: organizations.id,
                    label: `${organizations.name}`,
                };
            });
        }
    }

    onMount(async () => {
        const [successResponse, errorResponse] = await getAllOrganizations();
        organizations = getDataFromResponse(successResponse);
        defaultValue = value.organizations.map((organization) => {
            return {
                value: organization.id,
                label: organization.name,
            };
        });
    });
</script>

<Select
    {items}
    isMulti={true}
    bind:value={defaultValue}
    on:select={handleSelect}
    on:clear={handleClear}
/>
