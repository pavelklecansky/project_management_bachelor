<script lang="ts">
    import {Datatable} from "svelte-simple-datatables";
    import {goto, params, url} from "@roxi/routify";
    import {onMount} from "svelte";
    import CreateButton from "../../../components/core/CreateButton.svelte";
    import {error, success} from "../../../lib/notification";
    import {deleteGroupMember, deleteMember, getProject,} from "../../../lib/project.service";
    import type {Group, Project} from "../../../types/core.type";
    import type {User} from "../../../types/authentication.type";
    import {getDataFromResponse} from "../../../lib/utils";
    import Delete24 from "carbon-icons-svelte/lib/Delete24";

    let project = {} as Project;
    let members = [] as User[];
    let groupMembers = [] as Group[];
    let id;

    let memberRows;
    let groupMemberRows;

    const settings = {
        sortable: true,
        pagination: true,
        rowsPerPage: 10,
        noRows: "No data found",
        columnFilter: false,
        blocks: {
            searchInput: false,
        },
    };

    onMount(async () => {
        id = $params.id;
        const [success, errorMessage] = await getProject(id);
        if (!success || errorMessage) {
            error(errorMessage);
            $goto(`/`);
        } else {
            project = success!;
            members = project.members;
            groupMembers = project.memberGroups;
        }
    });

    const deleteMemberOnClick = async (idMember: string) => {
        let id = $params.id;
        const [successMessage, errorMessage] = await deleteMember(id, idMember);
        if (errorMessage) {
            error(errorMessage);
        } else {
            success(successMessage);
            project = getDataFromResponse(successMessage)!;
            members = project.members;
            groupMembers = project.memberGroups;
        }
    };
    const deleteGroupMemberOnClick = async (idMember: string) => {
        let id = $params.id;
        const [successMessage, errorMessage] = await deleteGroupMember(
            id,
            idMember
        );
        if (errorMessage) {
            error(errorMessage);
        } else {
            success(successMessage);
            project = getDataFromResponse(successMessage)!;
            members = project.members;
            groupMembers = project.memberGroups;
        }
    };
</script>

<div class="flex justify-end w-full mb-2">
    <a class="mr-4" href={$url(`./new-group-member/${id}`)}
        ><CreateButton text={"Add new group member"} /></a
    >
    <a class="mr-4" href={$url(`./new-member/${id}`)}
        ><CreateButton text={"Add new member"} /></a
    >
</div>

<div>
    <div>
        <h2 class="text-2xl mb-2">Members</h2>
        <Datatable
            {settings}
            data={members}
            bind:dataRows={memberRows}
            id={"table-1"}
        >
            <thead>
                <th data-key="name">Name</th>
                <th>Actions</th>
            </thead>
            <tbody>
                {#if memberRows}
                    {#each $memberRows as row}
                        <tr>
                            <td>{row.firstName} {row.lastName}</td>
                            <td>
                                <Delete24
                                    class="cursor-pointer"
                                    on:click={() => deleteMemberOnClick(row.id)}
                                />
                            </td>
                        </tr>
                    {/each}
                {/if}
            </tbody>
        </Datatable>
    </div>
    <div>
        <h2 class="text-2xl mt-10 mb-2">Group Members</h2>
        <Datatable
            {settings}
            data={groupMembers}
            bind:dataRows={groupMemberRows}
            id={"table-2"}
        >
            <thead>
                <th data-key="name">Name</th>
                <th>Actions</th>
            </thead>
            <tbody>
                {#if groupMemberRows}
                    {#each $groupMemberRows as row}
                        <tr>
                            <td>{row.name}</td>
                            <td>
                                <Delete24
                                    class="cursor-pointer"
                                    on:click={() =>
                                        deleteGroupMemberOnClick(row.id)}
                                />
                            </td>
                        </tr>
                    {/each}
                {/if}
            </tbody>
        </Datatable>
    </div>
</div>

<style>
    td{
        @apply w-1/2;
    }
</style>
