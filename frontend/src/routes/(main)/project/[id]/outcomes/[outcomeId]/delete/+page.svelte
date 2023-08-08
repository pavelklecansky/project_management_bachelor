<script lang="ts">
    import { page } from '$app/stores';
    import { goto } from '$app/navigation';
    import {error, success} from "$lib/notification";
    import {onMount} from "svelte";
    import {deleteOutcome} from '$lib/outcomes.service';

    onMount(async () => {
        let id = $page.params.outcomeId;
        let projectId = $page.params.id;
        const [successMessage, errorMessage] = await deleteOutcome(id);
        if (!successMessage || errorMessage) {
            error(errorMessage);
            goto(`/project/outcomes/${projectId}`);
        }
        success(successMessage);
        goto(`/project/outcomes/${projectId}`);
    });
</script>