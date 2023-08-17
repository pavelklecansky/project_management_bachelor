<script lang="ts">
	import { goto } from '$app/navigation';
	import Close from 'carbon-icons-svelte/lib/Close.svelte';

	export let outsideClickGotoPath;
	export let handleSubmit = (event) => {};
</script>

<div
	on:click={() => goto(outsideClickGotoPath)}
	class="fixed top-0 left-0 w-screen h-screen z-50 bg-gray-900 bg-opacity-75 origin-center flex justify-center items-center appear-done enter-done cursor-pointer"
>
	<div
		class="bg-white w-11/12 max-w-xl text-center pt-10 rounded shadow-lg appear-done enter-done relative cursor-default"
		on:click|stopPropagation={() => {}}
	>
		<div class="absolute top-0 right-0 cursor-pointer" on:click={() => goto(outsideClickGotoPath)}>
			<Close />
		</div>
		<form on:submit|preventDefault={handleSubmit} class="overflow-auto h-90vh">
			<slot />
		</form>
	</div>
</div>

<style>
	.h-90vh {
		max-height: 90vh;
	}
</style>
