<script lang="ts">
	import { page } from '$app/stores';
	import { afterNavigate, goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import { onMount } from 'svelte';
	import { deleteOrganization } from '$lib/organization.service';

	let previousPage: string = '/organizations';

	afterNavigate(({ from }) => {
		previousPage = from?.url.pathname || previousPage;
	});

	onMount(async () => {
		let id = $page.params.id;
		const [successMessage, errorMessage] = await deleteOrganization(id);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			goto(previousPage);
		}
		success(successMessage);
		goto(previousPage);
	});
</script>
