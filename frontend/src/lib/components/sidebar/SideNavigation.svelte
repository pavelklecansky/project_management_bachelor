<script>
    import Item from "./SidebarItem.svelte";
    import Building from "carbon-icons-svelte/lib/Building.svelte";
    import UserMultiple from "carbon-icons-svelte/lib/UserMultiple.svelte";
    import Group from "carbon-icons-svelte/lib/Group.svelte";
    import Dashboard from "carbon-icons-svelte/lib/Dashboard.svelte";
    import Phone from "carbon-icons-svelte/lib/Phone.svelte";
    import WatsonHealthStackedScrolling_1 from "carbon-icons-svelte/lib/WatsonHealthStackedScrolling_1.svelte";
    import {isActive} from "$lib/utils"
    import {isAdmin} from "$lib/auth";
    import { page } from '$app/stores';


    let userLinks = [];
    let adminLinks = [];

    $: adminLinks = [
        ["/users", "Users", UserMultiple],
        ["/groups", "Groups", Group],
        ["/organizations", "Organizations", Building],
    ].map(([path, name, icon]) => {
        return {
            name,
            href: path,
            active: isActive($page, path),
            icon,
        };
    });

    $: userLinks = [
        ["/", "Dashboard", Dashboard],
        ["/projects", "Projects", WatsonHealthStackedScrolling_1],
        ["/contacts", "Contacts", Phone],
    ].map(([path, name, icon]) => {
        return {
            name,
            href: path,
            active: isActive($page, path),
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
