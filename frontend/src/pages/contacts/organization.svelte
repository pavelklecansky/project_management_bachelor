<script lang="ts">
    import {Datatable} from "svelte-simple-datatables";
    import {afterPageLoad} from "@roxi/routify";
    import {getAllOrganizations} from "../../lib/organization.service";
    import {getDataFromResponse} from "../../lib/utils";

    const settings = {
        sortable: true,
        pagination: true,
        rowsPerPage: 50,
        noRows: "No data found",
        columnFilter: true,
    };
    let rows;
    let data = [];

    $afterPageLoad(async () => {
        const [successResponse, errorResponse] = await getAllOrganizations();
        data = getDataFromResponse(successResponse);
    });
</script>

<div class="h-full max-w-full">
    <Datatable {settings} {data} bind:dataRows={rows}>
        <thead>
            <th data-key="name">Name</th>
            <th data-key="email">Email</th>
            <th data-key="ico">IČO</th>
            <th data-key="phoneNumber">Phone number</th>
            <th data-key="note">Note</th>
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
                    </tr>
                {/each}
            {/if}
        </tbody>
    </Datatable>
</div>

<slot />

<style>
    td {
        text-align: center;
        padding: 4px 0;
    }
</style>
