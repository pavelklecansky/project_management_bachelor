<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import { onMount } from 'svelte';
	import { deleteResult } from '$lib/results.service';

	onMount(async () => {
		let id = $page.params.resultId;
		let projectId = $page.params.id;
		let outcomeId = $page.params.outcomeId;
		const [successMessage, errorMessage] = await deleteResult(id);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			goto(`/project/outcomes/${projectId}/${outcomeId}`);
		}
		success(successMessage);
		goto(`/project/outcomes/${projectId}/${outcomeId}`);
	});
</script>
