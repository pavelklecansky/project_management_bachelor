<script lang="ts">
    import {Datatable, DataHandler, Th, ThFilter} from "@vincjo/datatables";
    import {afterPageLoad} from "@roxi/routify";
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

<div class="h-full max-w-full">
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
                    </tr>
                {/each}
                </tbody>
            </table>
        </Datatable>
    {/if}
</div>

<slot/>

<style>
    td {
        text-align: center;
        padding: 4px 0;
    }
</style>
