<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../../lib/notification";
    import {onMount} from "svelte";
    import {deleteBudgetItem} from '../../../../../lib/budget.service';

    onMount(async () => {
        let id = $params.id;
        let itemId = $params.itemId;
        const [successMessage, errorMessage] = await deleteBudgetItem(itemId);
        if (!successMessage || errorMessage) {
            error(errorMessage);
            $goto(`/project/budget/${id}`);
        }
        success(successMessage);
        $goto(`/project/budget/${id}`);
    });
</script>
