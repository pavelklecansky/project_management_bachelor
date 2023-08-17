<script lang="ts">
	import { page } from '$app/stores';
	import { afterNavigate, goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import { onMount } from 'svelte';
	import { deleteGroup } from '$lib/groups.service';
	import { base } from '$app/paths';

	let previousPage: string = base;

	afterNavigate(({ from }) => {
		previousPage = from?.url.pathname || previousPage;
	});

	onMount(async () => {
		let id = $page.params.id;
		const [successMessage, errorMessage] = await deleteGroup(id);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			goto(previousPage);
		} else {
			success(successMessage);
			goto(previousPage);
		}
	});
</script>
