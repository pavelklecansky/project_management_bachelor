<script lang="ts">
	interface Props {
		placeholder?: string;
		value?: string;
		disabled?: boolean;
	}

	let { placeholder = '', value = $bindable(''), disabled = false }: Props = $props();
</script>

<input
	class="{disabled
		? 'cursor-not-allowed opacity-50'
		: ''} block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-500 focus:outline-none focus:ring"
	type="email"
	{placeholder}
	required
	bind:value
	{disabled}
/>
