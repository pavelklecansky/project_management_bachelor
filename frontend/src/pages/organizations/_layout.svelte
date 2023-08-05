<script>
    import {afterPageLoad, url} from "@roxi/routify";
    import {EditIcon, Trash2Icon} from "svelte-feather-icons";
    import {Datatable, DataHandler, Th, ThFilter} from "@vincjo/datatables";
    import CreateButton from "../../components/core/CreateButton.svelte";
    import {getAllOrganizations} from "../../lib/organization.service";
    import {getDataFromResponse} from "../../lib/utils";

    let handler;
    let rows;

    $afterPageLoad(async () => {
        const [successResponse, errorResponse] = await getAllOrganizations();
        const data = getDataFromResponse(successResponse);

        handler = new DataHandler(data, {rowsPerPage: 20});
        rows = handler.getRows();
    });
</script>

<div class="flex justify-end w-full mb-2">
    <a href={$url(`./create`)}
    >
        <CreateButton text={"Create new organization"}/>
    </a
    >
</div>

{#if rows}
    <Datatable {handler} pagination={true}>
        <table>
            <thead>
            <tr>
                <Th {handler} orderBy="name">Name</Th>
                <Th {handler} orderBy="email">Email</Th>
                <Th {handler} orderBy="ico">IČO</Th>
                <Th {handler} orderBy="phoneNumber">Phone number</Th>
                <Th {handler} orderBy="note">Note</Th>
                <th>Actions</th>
            </tr>
            <tr>
                <ThFilter {handler} filterBy="name"/>
                <ThFilter {handler} filterBy="email"/>
                <ThFilter {handler} filterBy="ico"/>
                <ThFilter {handler} filterBy="phoneNumber"/>
                <ThFilter {handler} filterBy="note"/>
            </tr>
            </thead>
            <tbody>
            {#each $rows as row}
                <tr>
                    <td>{row.name}</td>
                    <td>{row.email}</td>
                    <td>{row.ico}</td>
                    <td>{row.phoneNumber}</td>
                    <td>{row.note}</td>
                    <td class="flex justify-center">
                        <a href={$url(`./edit/${row.id}`)}
                        >
                            <EditIcon size="1.5x"/>
                        </a
                        >
                        <a class="mr-4" href={$url(`./delete/${row.id}`)}
                        >
                            <Trash2Icon size="1.5x"/>
                        </a
                        >
                    </td>
                </tr>
            {/each}
            </tbody>
        </table>
    </Datatable>
{/if}

<slot/>

<style>
    td {
        text-align: center;
        padding: 4px 0;
    }
</style>
