<script lang="ts">
	import { EditIcon, Trash2Icon } from 'svelte-feather-icons';
	import { Datatable, TableHandler, Th, ThFilter } from '@vincjo/datatables';
	import CreateButton from '$lib/components/core/CreateButton.svelte';

	interface Props {
		data: { organizations: any[] };
		children?: import('svelte').Snippet;
	}

	let { data, children }: Props = $props();

	console.log(data);

	let table = new TableHandler(data.organizations, { rowsPerPage: 20 });
</script>

<div class="flex justify-end w-full mb-2">
	<a href={`/organizations/create`}>
		<CreateButton text={'Create new organization'} />
	</a>
</div>

<Datatable {table} pagination={true}>
	<table>
		<thead>
			<tr>
				<Th {table} orderBy="name">Name</Th>
				<Th {table} orderBy="email">Email</Th>
				<Th {table} orderBy="ico">IČO</Th>
				<Th {table} orderBy="phoneNumber">Phone number</Th>
				<Th {table} orderBy="note">Note</Th>
				<th>Actions</th>
			</tr>
			<tr>
				<ThFilter {table} filterBy="name" />
				<ThFilter {table} filterBy="email" />
				<ThFilter {table} filterBy="ico" />
				<ThFilter {table} filterBy="phoneNumber" />
				<ThFilter {table} filterBy="note" />
			</tr>
		</thead>
		<tbody>
			{#each table.rows as row}
				<tr>
					<td>{row.name}</td>
					<td>{row.email}</td>
					<td>{row.ico}</td>
					<td>{row.phoneNumber}</td>
					<td>{row.note}</td>
					<td class="flex justify-center">
						<a href={`/organizations/${row.id}/edit`}>
							<EditIcon size="1.5x" />
						</a>
						<a class="mr-4" href={`/organizations/${row.id}/delete`}>
							<Trash2Icon size="1.5x" />
						</a>
					</td>
				</tr>
			{/each}
		</tbody>
	</table>
</Datatable>

{@render children?.()}

<style>
	td {
		text-align: center;
		padding: 4px 0;
	}
</style>
