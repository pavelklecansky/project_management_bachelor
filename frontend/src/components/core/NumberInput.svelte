<script lang="ts">
    export let value = 0;
    export let disabled = false;
    export let min = 0;
    export let max;
</script>

<input
                class="{disabled ? 'cursor-not-allowed opacity-50' : ''} block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-500 focus:outline-none focus:ring"
                type="number"
                {min}
                {max}
                required
                bind:value={value}
                {disabled}
            />