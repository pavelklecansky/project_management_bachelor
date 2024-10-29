<script lang="ts">
	interface Props {
		text?: string;
		full?: boolean;
		files: any;
	}

	let { text = 'Create', full = false, files = $bindable() }: Props = $props();
</script>

<label
	for="file-upload"
	class:w-full={full}
	class="px-4 py-2 leading-5 text-white transition-colors duration-200 transform bg-blue-500 rounded hover:bg-blue-600 focus:outline-none cursor-pointer"
>
	{text}
</label>
<input id="file-upload" type="file" class="hidden" bind:files />
