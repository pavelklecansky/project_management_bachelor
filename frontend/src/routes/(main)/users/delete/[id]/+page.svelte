<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import { deleteUser } from '$lib/user';
	import { onMount } from 'svelte';
	import { load } from '$lib/users.store';

	onMount(async () => {
		let id = $page.params.id;
		const [successMessage, errorMessage] = await deleteUser(id);
		if (!successMessage || errorMessage) {
			error(errorMessage);
			goto('./../');
		}
		success(successMessage);
		load();
		goto('./../');
	});
</script>
