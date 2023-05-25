<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../../lib/notification";
    import {onMount} from "svelte";
    import {deleteTask} from '../../../../../lib/schedule.service';

    onMount(async () => {
        let id = $params.id;
        let taskId = $params.taskId;
        const [successMessage, errorMessage] = await deleteTask(taskId);
        if (!successMessage || errorMessage) {
            error(errorMessage);
            $goto(`/project/schedule/${id}`);
        }
        success(successMessage);
        $goto(`/project/schedule/${id}`);
    });
</script>
