<script>
	/** @type {{children?: import('svelte').Snippet}} */
	let { children } = $props();
</script>

<div class="flex items-center justify-center h-screen">
	<div
		class="w-full border-gray-100 max-w-sm mx-auto overflow-hidden bg-white rounded-lg shadow-xl align-middle"
	>
		<div class="px-6 py-4">
			{@render children?.()}
		</div>
	</div>
</div>
