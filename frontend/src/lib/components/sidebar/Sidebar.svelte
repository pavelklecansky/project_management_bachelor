<script lang="ts">
	import { run } from 'svelte/legacy';

	import Navigation from './SideNavigation.svelte';
	import { hidden } from '$lib/nav';

	let sidebar = $state();

	function closeSidebar(sidebar) {
		sidebar.classList.add('translate-x-0');
		sidebar.classList.remove('-translate-x-full');
	}

	function openSidebar(sidebar) {
		sidebar.classList.remove('translate-x-0');
		sidebar.classList.add('-translate-x-full');
	}
	run(() => {
		if ($hidden) {
			if (sidebar) {
				openSidebar(sidebar);
			}
		} else {
			if (sidebar) {
				closeSidebar(sidebar);
			}
		}
	});
</script>

<aside
	id="sidebar"
	bind:this={sidebar}
	class="z-50 w-64 overflow-y-auto bg-gray-50 border-l flex-shrink-0 inset-y-0 left-0 transform transition duration-200 ease-in-out absolute lg:relative h-full"
>
	<div class="py-4">
		<a href={'/'} class="ml-6 text-lg font-bold text-gray-800"> Project managment </a>
		<Navigation />
	</div>
</aside>
