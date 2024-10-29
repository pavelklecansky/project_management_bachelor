<script lang="ts">
	export let placeholder = '';
	export let value = '';
	export let required = true;
	export let full = true;
</script>

<input
	class:w-full={full}
	class="block px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-500 focus:outline-none focus:ring"
	type="text"
	{placeholder}
	{required}
	bind:value
/>
