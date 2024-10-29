<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import { deleteTask } from '$lib/task.service';
	import { onMount } from 'svelte';
	import { load } from '$lib/projects.store';

	onMount(async () => {
		let id = $page.params.id;
		const [successMessage, errorMessage] = await deleteTask(id);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			await goto('/projects');
		}
		success(successMessage);
		await load();
		await goto('/projects');
	});
</script>
