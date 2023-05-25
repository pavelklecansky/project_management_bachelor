<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../../../lib/notification";
    import {onMount} from "svelte";
    import {deleteOutcomeCategory} from '../../../../../../lib/outcome-categories.service';

    onMount(async () => {
        let id = $params.outcomeId;
        let projectId = $params.id;
        const [successMessage, errorMessage] = await deleteOutcomeCategory(id);
        if (!successMessage || errorMessage) {
            error(errorMessage);
            $goto(`/project/outcomes/${projectId}`);
        }
        success(successMessage);
        $goto(`/project/outcomes/${projectId}`);
    });
</script>