<script lang="ts">
	export let title: string;
	export let url: string;
	export let active: any;
</script>

<li class="relative hover:bg-gray-100 hover:text-gray-900">
	<a
		class="px-6 py-3 inline-flex items-center w-full text-sm font-semibold text-gray-800 transition-colors duration-150 hover:text-gray-800 dark:hover:text-gray-200 dark:text-gray-100"
		href={url}
	>
		<slot />
		<span class="ml-4" class:font-bold={active}>{title}</span>
	</a>
</li>

<style>
	svg {
		color: black;
	}
</style>
