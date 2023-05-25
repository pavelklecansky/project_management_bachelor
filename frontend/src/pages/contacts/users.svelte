<script>
    import {users} from "../../lib/users.store";
    import {Datatable} from "svelte-simple-datatables";

    const settings = {
        sortable: true,
        pagination: true,
        rowsPerPage: 50,
        noRows: "No data found",
        columnFilter: true,
    };
    let rows;
</script>

<Datatable {settings} data={$users} bind:dataRows={rows}>
    <thead>
        <th data-key="firstName">First name</th>
        <th data-key="lastName">Last name</th>
        <th data-key="email">Email</th>
        <th data-key="phoneNumber">Phone number</th>
        <th data-key="note">Note</th>
    </thead>
    <tbody>
        {#if rows}
            {#each $rows as row}
                <tr>
                    <td>{row.firstName}</td>
                    <td>{row.lastName}</td>
                    <td>{row.email}</td>
                    <td>{row.phoneNumber}</td>
                    <td>{row.note}</td>
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
