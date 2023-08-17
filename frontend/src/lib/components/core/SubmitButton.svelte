<script lang="ts">
	export let text = 'OK';
	export let full = false;
	export let color = 'green';
</script>

<button
	class:w-full={full}
	class="px-4 py-2 leading-5 text-white transition-colors duration-200 transform bg-{color}-500 rounded hover:bg-{color}-600 focus:outline-none"
	type="submit">{text}</button
>
