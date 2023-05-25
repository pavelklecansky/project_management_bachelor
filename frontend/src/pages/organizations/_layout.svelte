<script>
    import {afterPageLoad, url} from "@roxi/routify";
    import {EditIcon, Trash2Icon} from "svelte-feather-icons";
    import {Datatable} from "svelte-simple-datatables";
    import CreateButton from "../../components/core/CreateButton.svelte";
    import {getAllOrganizations} from "../../lib/organization.service";
    import {getDataFromResponse} from "../../lib/utils";

    const settings = {
        sortable: true,
        pagination: true,
        rowsPerPage: 20,
        noRows: "No data found",
        columnFilter: true,
        scrollX: true,
    };
    let rows;
    let data = [];

    $afterPageLoad(async () => {
        const [successResponse, errorResponse] = await getAllOrganizations();
        data = getDataFromResponse(successResponse);
    });
</script>

<div class="flex justify-end w-full mb-2">
    <a href={$url(`./create`)}
        ><CreateButton text={"Create new organization"} /></a
    >
</div>

<Datatable {settings} {data} bind:dataRows={rows}>
    <thead>
        <th data-key="name">Name</th>
        <th data-key="email">Email</th>
        <th data-key="ico">IČO</th>
        <th data-key="phoneNumber">Phone number</th>
        <th data-key="note">Note</th>
        <th>Actions</th>
    </thead>
    <tbody>
        {#if rows}
            {#each $rows as row}
                <tr>
                    <td>{row.name}</td>
                    <td>{row.email}</td>
                    <td>{row.ico}</td>
                    <td>{row.phoneNumber}</td>
                    <td>{row.note}</td>
                    <td class="flex justify-center">
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

<slot />

<style>
    td {
        text-align: center;
        padding: 4px 0;
    }
</style>
