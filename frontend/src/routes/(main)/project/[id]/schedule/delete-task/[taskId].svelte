<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import { onMount } from 'svelte';
	import { deleteTask } from '$lib/schedule.service';

	onMount(async () => {
		let id = $page.params.id;
		let taskId = $page.params.taskId;
		const [successMessage, errorMessage] = await deleteTask(taskId);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			goto(`/project/schedule/${id}`);
		}
		success(successMessage);
		goto(`/project/schedule/${id}`);
	});
</script>
