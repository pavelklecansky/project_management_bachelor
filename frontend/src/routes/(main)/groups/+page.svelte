<script lang="ts">
	import { Datatable, TableHandler, Th, ThFilter } from '@vincjo/datatables';
	import { EditIcon, Trash2Icon } from 'svelte-feather-icons';
	import Information from 'carbon-icons-svelte/lib/Information.svelte';
	import CreateButton from '$lib/components/core/CreateButton.svelte';
	import type { Group } from '$lib/types/core.type';

	interface Props {
		data: { groups: Group[] };
	}

	let { data }: Props = $props();

	const table = new TableHandler(data.groups, { rowsPerPage: 50 });
</script>

<div class="flex justify-between m-2">
	<h1 class="text-2xl"></h1>
	<a href={'/groups/create'}>
		<CreateButton text={'Create new group'} />
	</a>
</div>
<Datatable {table} pagination={true}>
	<table>
		<thead>
			<tr>
				<Th {table} orderBy="name">Name</Th>
				<Th {table} orderBy="numberOfMembers">Number of Members</Th>
				<th>Actions</th>
			</tr>
			<tr>
				<ThFilter {table} filterBy="name" />
				<ThFilter {table} filterBy="last_name" />
			</tr>
		</thead>
		<tbody>
			{#each table.rows as row}
				<tr>
					<td>{row.name}</td>
					<td>{row.members.length}</td>
					<td class="flex justify-center gap-2">
						<a href={`/groups/${row.id}/info`}>
							<Information />
						</a>
						<a href={`/groups/${row.id}/edit`}>
							<EditIcon size="1.5x" />
						</a>
						<a class="mr-4" href={`/groups/${row.id}/delete`}>
							<Trash2Icon size="1.5x" />
						</a>
					</td>
				</tr>
			{/each}
		</tbody>
	</table>
</Datatable>

<style>
	td {
		text-align: center;
		padding: 4px 0;
	}
</style>
