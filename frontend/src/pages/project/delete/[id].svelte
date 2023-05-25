<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import {deleteProject} from "../../../lib/project.service";
    import {onMount} from "svelte";
    import {load} from "../../../lib/projects.store";

    onMount(async () => {
		let id = $params.id;
		const [successMessage, errorMessage] = await deleteProject(id);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			$goto("/projects");
		}
		success(successMessage);
		load();
		$goto("/projects");
	});
</script>
