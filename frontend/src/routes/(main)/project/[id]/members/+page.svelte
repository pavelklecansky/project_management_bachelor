<script lang="ts">
	import { Datatable, DataHandler, Th, ThFilter } from '@vincjo/datatables';
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';

	import { onMount } from 'svelte';
	import CreateButton from '$lib/components/core/CreateButton.svelte';
	import { error, success } from '$lib/notification';
	import { deleteGroupMember, deleteMember, getProject } from '$lib/project.service';
	import type { Project } from '$lib/types/core.type';
	import { getDataFromResponse } from '$lib/utils';
	import TrashCan from 'carbon-icons-svelte/lib/TrashCan.svelte';

	let project = {} as Project;
	let id;

	let memberRows;
	let groupMemberRows;
	let memberHandler;
	let groupMemberHandler;

	onMount(async () => {
		id = $page.params.id;
		const [success, errorMessage] = await getProject(id);
		if (!success || errorMessage) {
			error(errorMessage);
			await goto(`/`);
		} else {
			project = success!;
			memberHandler = new DataHandler(project.members, { rowsPerPage: 50 });
			memberRows = memberHandler.getRows();
			groupMemberHandler = new DataHandler(project.memberGroups, { rowsPerPage: 50 });
			groupMemberRows = groupMemberHandler.getRows();
		}
	});

	const deleteMemberOnClick = async (idMember: string) => {
		let id = $page.params.id;
		const [successMessage, errorMessage] = await deleteMember(id, idMember);
		if (errorMessage) {
			error(errorMessage);
		} else {
			success(successMessage);
			project = getDataFromResponse(successMessage)!;
			memberHandler = new DataHandler(project.members, { rowsPerPage: 50 });
			memberRows = memberHandler.getRows();
			groupMemberHandler = new DataHandler(project.memberGroups, { rowsPerPage: 50 });
			groupMemberRows = groupMemberHandler.getRows();
		}
	};
	const deleteGroupMemberOnClick = async (idMember: string) => {
		let id = $page.params.id;
		const [successMessage, errorMessage] = await deleteGroupMember(id, idMember);
		if (errorMessage) {
			error(errorMessage);
		} else {
			success(successMessage);
			project = getDataFromResponse(successMessage)!;
			memberHandler = new DataHandler(project.members, { rowsPerPage: 50 });
			memberRows = memberHandler.getRows();
			groupMemberHandler = new DataHandler(project.memberGroups, { rowsPerPage: 50 });
			groupMemberRows = groupMemberHandler.getRows();
		}
	};
</script>

<div class="flex justify-end w-full mb-2">
	<a class="mr-4" href={`./new-group-member/${id}`}>
		<CreateButton text={'Add new group member'} />
	</a>
	<a class="mr-4" href={`./new-member/${id}`}>
		<CreateButton text={'Add new member'} />
	</a>
</div>

<div>
	<div>
		<h2 class="text-2xl mb-2">Members</h2>
		{#if memberRows}
			<Datatable handler={memberHandler} pagination={true} id={'table-1'}>
				<table>
					<thead>
						<tr>
							<Th handler={memberHandler} orderBy="name">Name</Th>
							<th>Actions</th>
						</tr>
						<tr>
							<ThFilter handler={memberHandler} filterBy="name" />
						</tr>
					</thead>
					<tbody>
						{#if memberRows}
							{#each $memberRows as row}
								<tr>
									<td>{row.firstName} {row.lastName}</td>
									<td>
										<TrashCan class="cursor-pointer" on:click={() => deleteMemberOnClick(row.id)} />
									</td>
								</tr>
							{/each}
						{/if}
					</tbody>
				</table>
			</Datatable>
		{/if}
	</div>
	<div>
		<h2 class="text-2xl mt-10 mb-2">Group Members</h2>
		{#if groupMemberRows}
			<Datatable handler={groupMemberHandler} pagination={true} id={'table-2'}>
				<table>
					<thead>
						<tr>
							<Th handler={groupMemberHandler} orderBy="name">Name</Th>
							<th>Actions</th>
						</tr>
						<tr>
							<ThFilter handler={groupMemberHandler} filterBy="name" />
						</tr>
					</thead>
					<tbody>
						{#if groupMemberRows}
							{#each $groupMemberRows as row}
								<tr>
									<td>{row.name}</td>
									<td>
										<TrashCan
											class="cursor-pointer"
											on:click={() => deleteGroupMemberOnClick(row.id)}
										/>
									</td>
								</tr>
							{/each}
						{/if}
					</tbody>
				</table>
			</Datatable>
		{/if}
	</div>
</div>

<style>
	td {
		@apply w-1/2;
	}
</style>
