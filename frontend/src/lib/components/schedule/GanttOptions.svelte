<script>
	import { run } from 'svelte/legacy';

	import { createEventDispatcher, getContext } from 'svelte';

	/** @type {{options: any}} */
	let { options } = $props();

	const dispatch = createEventDispatcher();

	let { optionsStream } = getContext('options');
	run(() => {
		dispatch('change', options);
	});
	run(() => {
		dispatch('change', $optionsStream);
	});
</script>
