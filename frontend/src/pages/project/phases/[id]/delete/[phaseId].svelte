<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../../lib/notification";
    import {onMount} from "svelte";
    import {deletePhase} from '../../../../../lib/phase.service';

    onMount(async () => {
        let id = $params.phaseId;
        let projectId = $params.id;
        const [successMessage, errorMessage] = await deletePhase(id);
        if (!successMessage || errorMessage) {
            error(errorMessage);
            $goto(`/project/phases/${projectId}`);
        }
        success(successMessage);
        $goto(`/project/phases/${projectId}`);
    });
</script>