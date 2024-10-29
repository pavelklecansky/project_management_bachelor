<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';

	import { error, success } from '$lib/notification';
	import { onMount } from 'svelte';
	import { deleteBudgetItem } from '$lib/budget.service';

	onMount(async () => {
		let id = $page.params.id;
		let itemId = $page.params.budgetId;
		const [successMessage, errorMessage] = await deleteBudgetItem(itemId);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			await goto(`/project/${id}/budget`);
		}
		success(successMessage);
		await goto(`/project/${id}/budget`);
	});
</script>
