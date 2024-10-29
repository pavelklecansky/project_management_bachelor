<script lang="ts">
	export let value = '';
	export let required = true;
	export let title = '';
</script>

<input
	class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-500 focus:outline-none focus:ring"
	{title}
	type="date"
	{required}
	bind:value
/>
