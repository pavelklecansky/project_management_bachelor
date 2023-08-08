<script lang="ts">
    import { page } from '$app/stores';
    import { goto } from '$app/navigation';
    import {error, success} from "$lib/notification";
    import {onMount} from "svelte";
    import {deleteBudgetCategory} from '$lib/budget.service';

    onMount(async () => {
		let id = $page.params.id;
		let categoryId = $page.params.budgetId;
		const [successMessage, errorMessage] = await deleteBudgetCategory(categoryId);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			goto(`/project/${id}/budget`);
		}
		success(successMessage);
		goto(`/project/${id}/budget`);
	});
</script>