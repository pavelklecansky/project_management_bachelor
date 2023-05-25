<script>
    import {isActive, params, url} from "@roxi/routify";

    import Folder24 from "carbon-icons-svelte/lib/Folder24";
    import Group24 from "carbon-icons-svelte/lib/Group24";
    import Dashboard24 from "carbon-icons-svelte/lib/Dashboard24";
    import CategoryNewEach24 from "carbon-icons-svelte/lib/CategoryNewEach24";
    import CertificateCheck24 from "carbon-icons-svelte/lib/CertificateCheck24";
    import CalendarHeatMap24 from "carbon-icons-svelte/lib/CalendarHeatMap24";
    import Money24 from "carbon-icons-svelte/lib/Money24";


    $: urls = [
        [$url("/project/:id", $params), "Main", Dashboard24],
        [$url("/project/members/:id", $params), "Members", Group24],
        [$url("/project/files/:id", $params), "Files", Folder24],
        [$url("/project/phases/:id", $params), "Phases", CategoryNewEach24],
        [$url("/project/outcomes/:id", $params), "Outcomes", CertificateCheck24],
        [$url("/project/schedule/:id", $params), "Schedule", CalendarHeatMap24],
        [$url("/project/budget/:id", $params), "Budget", Money24],
    ].map(([path, name, icon]) => {
        return {
            name,
            href: path,
            active: $isActive(path),
            icon,
        };
    });
</script>

<div class="flex border-b border-gray-200 mb-4">
    {#each urls as {name, href, active, icon}}
        <a {href}>
            <button
                    class:active
                    class="flex items-center h-10 px-2 py-2 -mb-px text-center text-gray-700 hover:border-gray-400 bg-transparent border-b-2 sm:px-4 -px-1 whitespace-nowrap focus:outline-none"
            >
                <svelte:component this={icon}/>
                <span class="mx-1 text-sm sm:text-base"> {name} </span>
            </button>
        </a>
    {/each}
</div>

<slot/>

<style>
    .active {
        @apply text-blue-600;
        @apply border-blue-500;
    }
</style>
