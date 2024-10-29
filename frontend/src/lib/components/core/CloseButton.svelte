<script lang="ts">
	import { createBubbler } from 'svelte/legacy';

	const bubble = createBubbler();
	interface Props {
		text?: string;
	}

	let { text = 'OK' }: Props = $props();
</script>

<button
	onclick={bubble('click')}
	class="px-4 py-2 leading-5 text-white transition-colors duration-200 transform bg-gray-500 rounded hover:bg-gray-600 focus:outline-none"
	type="button">{text}</button
>
