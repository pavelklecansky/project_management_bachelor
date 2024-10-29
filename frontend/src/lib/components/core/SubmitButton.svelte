<script lang="ts">
	interface Props {
		text?: string;
		full?: boolean;
		color?: string;
	}

	let { text = 'OK', full = false, color = 'green' }: Props = $props();
</script>

<button
	class:w-full={full}
	class="px-4 py-2 leading-5 text-white transition-colors duration-200 transform bg-{color}-500 rounded hover:bg-{color}-600 focus:outline-none"
	type="submit">{text}</button
>
