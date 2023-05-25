<script lang="ts">
    import {isActive, url} from "@roxi/routify";

    import Building24 from "carbon-icons-svelte/lib/Building24";
    import Group24 from "carbon-icons-svelte/lib/Group24";

    $: urls = [
        ["./users", "Users", Group24],
        ["./organization", "Organization", Building24],
    ].map(([path, name, icon]) => {
        return {
            name,
            href: $url(path),
            active: $isActive(path),
            icon,
        };
    });
</script>

<div class="flex border-b border-gray-200 mb-4">
    {#each urls as { name, href, active, icon }}
        <a {href}>
            <button
                class:active
                class="flex items-center h-10 px-2 py-2 -mb-px text-center text-gray-700 hover:border-gray-400 bg-transparent border-b-2 sm:px-4 -px-1 whitespace-nowrap focus:outline-none"
            >
                <svelte:component this={icon} />
                <span class="mx-1 text-sm sm:text-base"> {name} </span>
            </button>
        </a>
    {/each}
</div>

<slot />

<style>
    .active {
        @apply text-blue-600;
        @apply border-blue-500;
    }
</style>
