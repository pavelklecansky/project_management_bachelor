<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import {onMount} from "svelte";
    import {deleteGroup} from "../../../lib/groups.service";

    onMount(async () => {
        let id = $params.id;
        const [successMessage, errorMessage] = await deleteGroup(id);
        if (!successMessage || errorMessage) {
            error(errorMessage);
            $goto("./../");
        } else {
            success(successMessage);
            $goto("./../");
        }

    });
</script>
