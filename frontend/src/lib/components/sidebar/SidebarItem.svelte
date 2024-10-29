<script lang="ts">
	interface Props {
		title: string;
		url: string;
		active: any;
		children?: import('svelte').Snippet;
	}

	let { title, url, active, children }: Props = $props();
</script>

<li class="relative hover:bg-gray-100 hover:text-gray-900">
	<a
		class="px-6 py-3 inline-flex items-center w-full text-sm font-semibold text-gray-800 transition-colors duration-150 hover:text-gray-800"
		href={url}
	>
		{@render children?.()}
		<span class="ml-4" class:font-bold={active}>{title}</span>
	</a>
</li>

<style>
	svg {
		color: black;
	}
</style>
