<script lang="ts">
	interface Props {
		placeholder?: string;
		value?: string;
		resize?: boolean;
		required?: boolean;
	}

	let {
		placeholder = '',
		value = $bindable(''),
		resize = false,
		required = true
	}: Props = $props();
</script>

<textarea
	class:resize-none={!resize}
	class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-500 focus:outline-none focus:ring"
	type="text"
	{placeholder}
	{required}
	rows="3"
	bind:value
></textarea>
