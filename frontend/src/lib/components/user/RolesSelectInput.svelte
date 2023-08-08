<script lang="ts">
    import {onMount} from "svelte";
    import Select from "svelte-select";
    import type {User} from "$lib/types/authentication.type";
    import {Role} from '$lib/types/role.enum';

    export let value = {} as User;

    let roles = [];
    let items = [];
    let defaultValue = [];

    function handleSelect(event) {
        const userRoles = event?.detail?.map((item) => {
            return item.value;
        });
        value.roles = userRoles || [];
    }

    function handleClear() {
        value.roles = [] as Role[];
    }

    $: {
        if (roles) {
            items = roles.map((role) => {
                return {
                    value: role,
                    label: role,
                };
            });
        }
    }

    onMount(async () => {
        roles = (Object.keys(Role) as Array<keyof typeof Role>);

        defaultValue = value.roles.map((role) => {
            return {
                value: role,
                label: role,
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
