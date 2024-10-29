<script lang="ts">
	import OverflowMenuVertical from 'carbon-icons-svelte/lib/OverflowMenuVertical.svelte';

	interface Props {
		hidden?: boolean;
		children?: import('svelte').Snippet;
	}

	let { hidden = $bindable(true), children }: Props = $props();
</script>

<button
	class="absolute top-0 right-0 z-10 block p-1 text-gray-700 border"
	onclick={() => (hidden = !hidden)}
>
	<OverflowMenuVertical />
</button>

<div class="origin-top-right absolute right-0 mt-3 z-56 w-56 rounded-md shadow-lg" class:hidden>
	<div class="rounded-md bg-white shadow-xs">
		<div class="py-1">
			{@render children?.()}
		</div>
	</div>
</div>
