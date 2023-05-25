<script>
    import Item from "./SidebarItem.svelte";
    import {isActive, url} from "@roxi/routify";
    import Building24 from "carbon-icons-svelte/lib/Building24";
    import UserMultiple24 from "carbon-icons-svelte/lib/UserMultiple24";
    import Group24 from "carbon-icons-svelte/lib/Group24";
    import Dashboard24 from "carbon-icons-svelte/lib/Dashboard24";
    import Phone24 from "carbon-icons-svelte/lib/Phone24";
    import WatsonHealthStackedScrolling_124 from "carbon-icons-svelte/lib/WatsonHealthStackedScrolling_124";

    import {isAdmin} from "../../lib/auth";

    let userLinks = [];
    let adminLinks = [];

    $: adminLinks = [
        ["/users", "Users", UserMultiple24],
        ["/groups", "Groups", Group24],
        ["/organizations", "Organizations", Building24],
    ].map(([path, name, icon]) => {
        return {
            name,
            href: $url(path),
            active: $isActive(path),
            icon,
        };
    });

    $: userLinks = [
        ["/index", "Dashboard", Dashboard24],
        ["/projects", "Projects", WatsonHealthStackedScrolling_124],
        ["/contacts", "Contacts", Phone24],
    ].map(([path, name, icon]) => {
        return {
            name,
            href: $url(path),
            active: $isActive(path),
            icon,
        };
    });
</script>

<ul class="mt-4">
    {#if isAdmin()}
        <div>
            <h3
                    class="px-6 py-1 inline-flex items-center w-full text-lg font-semibold text-gray-800"
            >
                Admin
            </h3>
            {#each adminLinks as {name, href, active, icon}}
                <Item title={name} url={href} {active}>
                    <svelte:component this={icon}/>
                </Item>
            {/each}
        </div>
    {/if}

    <div>
        <h3
                class="px-6 py-3 inline-flex items-center w-full text-lg font-semibold text-gray-800"
        >
            General
        </h3>
        {#each userLinks as {name, href, active, icon}}
            <Item title={name} url={href} {active}>
                <svelte:component this={icon}/>
            </Item>
        {/each}
    </div>
</ul>
