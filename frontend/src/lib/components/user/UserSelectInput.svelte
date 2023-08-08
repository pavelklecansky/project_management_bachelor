<script lang="ts">
    import Select from "svelte-select";
    import {users} from "$lib/users.store";
    import {isEmptyObjectOrNull} from "$lib/utils";
    import type {User} from "$lib/types/authentication.type";

    export let value = {} as User;
    export let disable = false;

    let items = [];
    let defaultValue = {
        value: "",
        label: "",
    };

    function handleSelect(event) {
        const userId = event.detail.value;
        value = $users.find((element) => element.id === userId);
    }

    function handleClear() {
        value = {} as User;
    }

    $: {
        if ($users) {
            items = $users.map((user) => {
                return {
                    value: user.id,
                    label: `${user.firstName} ${user.lastName}`,
                };
            });
        }

        if (!isEmptyObjectOrNull(value)) {
            defaultValue = {
                value: value.id || "",
                label: `${value.firstName || ""} ${value.lastName || ""}`,
            };
        }
    }
</script>

<Select
    value={defaultValue}
    {items}
    on:select={handleSelect}
    isDisabled={disable}
    on:clear={handleClear}
/>
