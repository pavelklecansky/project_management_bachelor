<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../../../../lib/notification";
    import {onMount} from "svelte";
    import {deleteResult} from '../../../../../../../lib/results.service';

    onMount(async () => {
        let id = $params.resultId;
        let projectId = $params.id;
        let outcomeId = $params.outcomeId;
        const [successMessage, errorMessage] = await deleteResult(id);
        if (!successMessage || errorMessage) {
            error(errorMessage);
            $goto(`/project/outcomes/${projectId}/${outcomeId}`);
        }
        success(successMessage);
        $goto(`/project/outcomes/${projectId}/${outcomeId}`);
    });
</script>