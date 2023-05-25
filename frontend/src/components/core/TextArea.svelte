<script lang="ts">
    export let placeholder = "";
    export let value = "";
    export let resize = false;
    export let required = true;
</script>

<textarea
    class:resize-none={!resize}
    class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md dark:bg-gray-800 dark:border-gray-600 dark:placeholder-gray-400 focus:border-blue-500 dark:focus:border-blue-500 focus:outline-none focus:ring"
    type="text"
    {placeholder}
    {required}
    rows="3"
    bind:value
/>
