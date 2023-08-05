<script>
    import {isActive, params, url} from "@roxi/routify";

    import Folder from "carbon-icons-svelte/lib/Folder.svelte";
    import Group from "carbon-icons-svelte/lib/Group.svelte";
    import Dashboard from "carbon-icons-svelte/lib/Dashboard.svelte";
    import CategoryNewEach from "carbon-icons-svelte/lib/CategoryNewEach.svelte";
    import CertificateCheck from "carbon-icons-svelte/lib/CertificateCheck.svelte";
    import CalendarHeatMap from "carbon-icons-svelte/lib/CalendarHeatMap.svelte";
    import Money from "carbon-icons-svelte/lib/Money.svelte";


    $: urls = [
        [$url("/project/:id", $params), "Main", Dashboard],
        [$url("/project/members/:id", $params), "Members", Group],
        [$url("/project/files/:id", $params), "Files", Folder],
        [$url("/project/phases/:id", $params), "Phases", CategoryNewEach],
        [$url("/project/outcomes/:id", $params), "Outcomes", CertificateCheck],
        [$url("/project/schedule/:id", $params), "Schedule", CalendarHeatMap],
        [$url("/project/budget/:id", $params), "Budget", Money],
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
