<script lang="ts">
	import { Datatable, DataHandler, Th, ThFilter } from '@vincjo/datatables';
	import { EditIcon, Trash2Icon } from 'svelte-feather-icons';
	import Information from 'carbon-icons-svelte/lib/Information.svelte';
	import { afterNavigate } from '$app/navigation';
	import { getGroups } from '$lib/groups.service';
	import { getDataFromResponse } from '$lib/utils';
	import CreateButton from '$lib/components/core/CreateButton.svelte';

	let handler;
	let rows;

	afterNavigate(async () => {
		const [successResponse, errorResponse] = await getGroups();
		const data = getDataFromResponse(successResponse);
		handler = new DataHandler(data, { rowsPerPage: 50 });
		rows = handler.getRows();
	});
</script>

<div class="flex justify-between m-2">
	<h1 class="text-2xl" />
	<a href={'/groups/create'}>
		<CreateButton text={'Create new group'} />
	</a>
</div>
{#if rows}
	<Datatable {handler} pagination={true}>
		<table>
			<thead>
				<tr>
					<Th {handler} orderBy="name">Name</Th>
					<Th {handler} orderBy="numberOfMembers">Number of Members</Th>
					<th>Actions</th>
				</tr>
				<tr>
					<ThFilter {handler} filterBy="name" />
					<ThFilter {handler} filterBy="last_name" />
				</tr>
			</thead>
			<tbody>
				{#each $rows as row}
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
{/if}

<style>
	td {
		text-align: center;
		padding: 4px 0;
	}
</style>
