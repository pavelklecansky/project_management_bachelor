<script lang="ts">
    import {users} from "../../lib/users.store";
    import {Datatable} from "svelte-simple-datatables";
    import {EditIcon, Trash2Icon} from "svelte-feather-icons";
    import {url} from "@roxi/routify";
    import CreateButton from "../../components/core/CreateButton.svelte";
    import PasscodePopup from '../../components/user/PasscodePopup.svelte';
    import {getContext} from 'svelte';
    import {generatePasscode} from '../../lib/user';
    import {error} from '../../lib/notification';
    import {getDataFromResponse} from '../../lib/utils';

    const {open} = getContext('simple-modal');

    const settings = {
        sortable: true,
        pagination: true,
        rowsPerPage: 50,
        noRows: "No data found",
        columnFilter: true,
    };
    let rows;


    const generatePasscodeOnClick = async () => {
        const [generateSuccess, generateError] = await generatePasscode()
        if (generateError) {
            error(generateError);
        } else {
            open(PasscodePopup, {message: getDataFromResponse(generateSuccess).passcode})
        }

    }
</script>

<div class="flex justify-end w-full mb-2">
    <CreateButton text={"Generate passcode"} on:click={generatePasscodeOnClick}/>
</div>


<Datatable {settings} data={$users} bind:dataRows={rows}>
    <thead>
    <tr>
        <th data-key="firstName">First name</th>
        <th data-key="lastName">Last name</th>
        <th data-key="email">Email</th>
        <th data-key="roles">Roles</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    {#if rows}
        {#each $rows as row}
            <tr>
                <td>{row.firstName}</td>
                <td>{row.lastName}</td>
                <td>{row.email}</td>
                <td>{row.roles}</td>
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
    {/if}
    </tbody>
</Datatable>

<slot/>

<style>
    td {
        text-align: center;
        padding: 4px 0;
    }
</style>
