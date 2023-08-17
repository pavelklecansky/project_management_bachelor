<script>
	import UserProfile from '$lib/components/user/UserProfile.svelte';
	import UserEdit from '$lib/components/user/UserEdit.svelte';
	import ChangePassword from '$lib/components/user/ChangePassword.svelte';
</script>

<section class="mt-2">
	<div class="grid grid-cols-1 gap-6 lg:grid-cols-2 mb-6">
		<UserEdit />
		<UserProfile />
	</div>
	<ChangePassword />
</section>
