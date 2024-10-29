<script lang="ts">
	interface Props {
		text?: string;
		full?: boolean;
	}

	let { text = 'Delete', full = false }: Props = $props();
</script>

<button
	class:w-full={full}
	class="px-4 py-2 leading-5 text-white transition-colors duration-200 transform bg-red-500 rounded hover:bg-red-600 focus:outline-none"
	type="button">{text}</button
>
