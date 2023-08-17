<script lang="ts">
	export let text = 'Create';
	export let full = false;
	export let color = 'blue';
</script>

<button
	on:click
	class:w-full={full}
	class="px-2 py-2 leading-5 text-white transition-colors duration-200 transform bg-{color}-500 rounded hover:bg-{color}-600 focus:outline-none"
	type="button">{text}</button
>
