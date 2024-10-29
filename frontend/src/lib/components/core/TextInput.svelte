<script lang="ts">
	interface Props {
		placeholder?: string;
		value?: string;
		required?: boolean;
		full?: boolean;
	}

	let { placeholder = '', value = $bindable(''), required = true, full = true }: Props = $props();
</script>

<input
	class:w-full={full}
	class="block px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-500 focus:outline-none focus:ring"
	type="text"
	{placeholder}
	{required}
	bind:value
/>
