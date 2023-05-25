<script lang="ts">
    import {Datatable} from "svelte-simple-datatables";
    import {EditIcon, Trash2Icon} from "svelte-feather-icons";
    import Information24 from "carbon-icons-svelte/lib/Information24";
    import {afterPageLoad, url} from "@roxi/routify";
    import {getGroups} from "../../lib/groups.service";
    import {getDataFromResponse} from "../../lib/utils";
    import type {Group} from "../../types/core.type";
    import CreateButton from "../../components/core/CreateButton.svelte";

    const settings = {
        sortable: true,
        pagination: true,
        rowsPerPage: 50,
        noRows: "No data found",
        columnFilter: true,
    };
    let rows;
    let data = [] as Group[];

    $afterPageLoad(async () => {
        const [successResponse, errorResponse] = await getGroups();
        data = getDataFromResponse(successResponse);
    });
</script>

<div class="flex justify-between m-2">
    <h1 class="text-2xl"> </h1>
    <a href={$url("./create")}>
        <CreateButton text={"Create new group"} />
    </a>
</div>
<Datatable {settings} {data} bind:dataRows={rows}>
    <thead>
    <th data-key="name">Name</th>
    <th data-key="numberOfMembers">Number of Members</th>
    <th>Actions</th>
    </thead>
    <tbody>
    {#if rows}
        {#each $rows as row}
            <tr>
                <td>{row.name}</td>
                <td>{row.members.length}</td>
                <td class="flex justify-center gap-2">
                    <a href={$url(`./info/${row.id}`)}><Information24 /></a>
                    <a href={$url(`./edit/${row.id}`)}
                    ><EditIcon size="1.5x" /></a
                    >
                    <a class="mr-4" href={$url(`./delete/${row.id}`)}
                    ><Trash2Icon size="1.5x" /></a
                    >
                </td>
            </tr>
        {/each}
    {/if}
    </tbody>
</Datatable>


<style>
    td {
        text-align: center;
        padding: 4px 0;
    }
</style>
