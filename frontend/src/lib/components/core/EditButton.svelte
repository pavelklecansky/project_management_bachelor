<script lang="ts">
	export let text = 'OK';
	export let full = false;
</script>

<button
	class:w-full={full}
	class="px-4 py-2 leading-5 text-white transition-colors duration-200 transform bg-green-500 rounded hover:bg-green-600 focus:outline-none"
	type="button">{text}</button
>
