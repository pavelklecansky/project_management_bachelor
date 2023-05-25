<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import {deleteUser} from "../../../lib/user";
    import {onMount} from "svelte";
    import {load} from "../../../lib/users.store";

    onMount(async () => {
		let id = $params.id;
		const [successMessage, errorMessage] = await deleteUser(id);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			$goto("./../");
		}
		success(successMessage);
		load();
		$goto("./../");
	});
</script>
