<script lang="ts">
	import { createBubbler } from 'svelte/legacy';

	const bubble = createBubbler();
	interface Props {
		text?: string;
		full?: boolean;
		color?: string;
	}

	let { text = 'Create', full = false, color = 'blue' }: Props = $props();
</script>

<button
	onclick={bubble('click')}
	class:w-full={full}
	class="px-2 py-2 leading-5 text-white transition-colors duration-200 transform bg-{color}-500 rounded hover:bg-{color}-600 focus:outline-none"
	type="button">{text}</button
>
