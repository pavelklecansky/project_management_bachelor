<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../../lib/notification";
    import {onMount} from "svelte";
    import {deleteBudgetCategory} from '../../../../../lib/budget.service';

    onMount(async () => {
		let id = $params.id;
		let categoryId = $params.categoryId;
		const [successMessage, errorMessage] = await deleteBudgetCategory(categoryId);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			$goto(`/project/budget/${id}`);
		}
		success(successMessage);
		$goto(`/project/budget/${id}`);
	});
</script>