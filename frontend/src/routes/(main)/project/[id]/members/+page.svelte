<script lang="ts">
	import { Datatable, TableHandler, Th, ThFilter } from '@vincjo/datatables';
	import { page } from '$app/stores';
	import { invalidateAll } from '$app/navigation';

	import CreateButton from '$lib/components/core/CreateButton.svelte';
	import { error } from '$lib/notification';
	import { deleteGroupMember, deleteMember } from '$lib/project.service';
	import type { Project } from '$lib/types/core.type';
	import TrashCan from 'carbon-icons-svelte/lib/TrashCan.svelte';

	interface Props {
		data: { project: Project };
	}

	let { data }: Props = $props();

	let memberTable = new TableHandler(data.project.members, { rowsPerPage: 50 });
	let groupMemberTable = new TableHandler(data.project.memberGroups, { rowsPerPage: 50 });

	const deleteMemberOnClick = async (idMember: string) => {
		let id = $page.params.id;
		const [_, errorMessage] = await deleteMember(id, idMember);
		if (errorMessage) {
			error(errorMessage);
		} else {
			await invalidateAll();
		}
	};

	const deleteGroupMemberOnClick = async (idMember: string) => {
		let id = $page.params.id;
		const [_, errorMessage] = await deleteGroupMember(id, idMember);
		if (errorMessage) {
			error(errorMessage);
		} else {
			await invalidateAll();
		}
	};
</script>

<div class="flex justify-end w-full mb-2">
	<a class="mr-4" href={`./members/new-group-member`}>
		<CreateButton text={'Add new group member'} />
	</a>
	<a class="mr-4" href={`./members/new-member`}>
		<CreateButton text={'Add new member'} />
	</a>
</div>

<div>
	<div>
		<h2 class="text-2xl mb-2">Members</h2>
		<Datatable table={memberTable} pagination={true} id={'table-1'}>
			<table>
				<thead>
					<tr>
						<Th table={memberTable} orderBy="name">Name</Th>
						<th>Actions</th>
					</tr>
					<tr>
						<ThFilter table={memberTable} filterBy="name" />
					</tr>
				</thead>
				<tbody>
					{#each memberTable.rows as row}
						<tr>
							<td>{row.firstName} {row.lastName}</td>
							<td>
								<TrashCan class="cursor-pointer" on:click={() => deleteMemberOnClick(row.id)} />
							</td>
						</tr>
					{/each}
				</tbody>
			</table>
		</Datatable>
	</div>
	<div>
		<h2 class="text-2xl mt-10 mb-2">Group Members</h2>

		<Datatable table={groupMemberTable} pagination={true} id={'table-2'}>
			<table>
				<thead>
					<tr>
						<Th table={groupMemberTable} orderBy="name">Name</Th>
						<th>Actions</th>
					</tr>
					<tr>
						<ThFilter table={groupMemberTable} filterBy="name" />
					</tr>
				</thead>
				<tbody>
					{#each groupMemberTable.rows as row}
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
				</tbody>
			</table>
		</Datatable>
	</div>
</div>

<style>
	td {
		@apply w-1/2;
	}
</style>
