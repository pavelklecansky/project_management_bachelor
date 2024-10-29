<script lang="ts">
	import UserMenu from './UserMenu.svelte';
	import { hidden } from '$lib/nav';
	import { MenuIcon } from 'svelte-feather-icons';

	let outerWidth = $state(0);

	const toggleOnClick = () => {
		$hidden = !$hidden;
	};
</script>

<svelte:window bind:outerWidth />
<header class="z-10 py-4 bg-white border-b border-gray-100">
	<div class="flex items-center justify-between h-full px-6">
		{#if outerWidth < 1024}
			<div onclick={toggleOnClick}><MenuIcon size="3x" /></div>
		{:else}
			<div></div>
		{/if}

		<ul class="flex items-center flex-shrink-0 space-x-6">
			<UserMenu />
		</ul>
	</div>
</header>
