<script lang="ts">
    import { page } from '$app/stores';
    import { goto } from '$app/navigation';
    import {error, success} from "$lib/notification";
    import {onMount} from "svelte";
    import {deletePhase} from '$lib/phase.service';

    onMount(async () => {
        let id = $page.params.phaseId;
        let projectId = $page.params.id;
        const [successMessage, errorMessage] = await deletePhase(id);
        if (!successMessage || errorMessage) {
            error(errorMessage);
            goto(`/project/${projectId}/phases`);
        }
        success(successMessage);
        goto(`/project/${projectId}/phases`);
    });
</script>