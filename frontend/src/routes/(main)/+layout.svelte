<script>
	import { run } from 'svelte/legacy';

	import Header from '$lib/components/header/Header.svelte';
	import Sidebar from '$lib/components/sidebar/Sidebar.svelte';
	import { goto } from '$app/navigation';
	import { authState } from '$lib/auth';
	import { hidden } from '$lib/nav';
	import { onMount } from 'svelte';
	import { SvelteToast } from '@zerodevx/svelte-toast';
	import { Modal } from 'svelte-simple-modal';
	import '../../global.css';
	/** @type {{children?: import('svelte').Snippet}} */
	let { children } = $props();

	let outerWidth = $state(0);

	onMount(() => {
		$hidden = outerWidth < 1024;
	});

	run(() => {
		if ($hidden) {
			if (outerWidth >= 1024) {
				$hidden = false;
			}
		}
	});
</script>

<svelte:window bind:outerWidth />
<div
	onclick={() => ($hidden = true)}
	class="{$hidden
		? 'hidden'
		: 'block'} fixed inset-0 top-0 left-0 w-screen h-screen z-20 transition-opacity bg-black opacity-50 lg:hidden"
></div>

<SvelteToast />
<Modal>
	{#if $authState.isSignedIn}
		<div id="app" class="flex h-screen flex-wrap">
			<div class="lg:w-2/12 overflow-hidden">
				<Sidebar />
			</div>
			<section class="flex flex-col flex-1 lg:w-10/12 w-full overflow-hidden">
				<Header />
				<main class="px-6 mt-2 mb-16 h-full">
					{@render children?.()}
				</main>
				<footer></footer>
			</section>
		</div>
	{:else}
		{goto('/authentication') || ''}
	{/if}
</Modal>
