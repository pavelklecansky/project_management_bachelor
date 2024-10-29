<script lang="ts">
	interface Props {
		value?: number;
		disabled?: boolean;
		min?: number;
		max: any;
	}

	let { value = $bindable(0), disabled = false, min = 0, max }: Props = $props();
</script>

<input
	class="{disabled
		? 'cursor-not-allowed opacity-50'
		: ''} block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-500 focus:outline-none focus:ring"
	type="number"
	{min}
	{max}
	required
	bind:value
	{disabled}
/>
