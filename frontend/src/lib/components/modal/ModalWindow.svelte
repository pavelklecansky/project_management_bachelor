<script lang="ts">
	import { stopPropagation, preventDefault } from 'svelte/legacy';

	import { goto } from '$app/navigation';
	import Close from 'carbon-icons-svelte/lib/Close.svelte';

	interface Props {
		outsideClickGotoPath: any;
		handleSubmit?: any;
		children?: import('svelte').Snippet;
	}

	let { outsideClickGotoPath, handleSubmit = (event) => {}, children }: Props = $props();
</script>

<div
	onclick={() => goto(outsideClickGotoPath)}
	class="fixed top-0 left-0 w-screen h-screen z-50 bg-gray-900 bg-opacity-75 origin-center flex justify-center items-center appear-done enter-done cursor-pointer"
>
	<div
		class="bg-white w-11/12 max-w-xl text-center pt-10 rounded shadow-lg appear-done enter-done relative cursor-default"
		onclick={stopPropagation(() => {})}
	>
		<div class="absolute top-0 right-0 cursor-pointer" onclick={() => goto(outsideClickGotoPath)}>
			<Close />
		</div>
		<form onsubmit={preventDefault(handleSubmit)} class="overflow-auto h-90vh">
			{@render children?.()}
		</form>
	</div>
</div>

<style>
	.h-90vh {
		max-height: 90vh;
	}
</style>
