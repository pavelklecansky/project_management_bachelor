<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import {onMount} from "svelte";
    import {deleteOrganization} from "../../../lib/organization.service";

    onMount(async () => {
		let id = $params.id;
		const [successMessage, errorMessage] = await deleteOrganization(id);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			$goto("./../");
		}
		success(successMessage);
		$goto("./../");
	});
</script>
