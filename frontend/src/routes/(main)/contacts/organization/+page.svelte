<script lang="ts">
	import { Datatable, TableHandler, Th, ThFilter } from '@vincjo/datatables';
	import type { Organization } from '$lib/types/core.type';

	interface Props {
		data: { organizations: Organization[] };
		children?: import('svelte').Snippet;
	}

	let { data, children }: Props = $props();

	let table = new TableHandler(data.organizations, { rowsPerPage: 20 });
</script>

<div class="h-full max-w-full">
	<Datatable {table} pagination={true}>
		<table>
			<thead>
				<tr>
					<Th {table} orderBy="name">Name</Th>
					<Th {table} orderBy="email">Email</Th>
					<Th {table} orderBy="ico">IČO</Th>
					<Th {table} orderBy="phoneNumber">Phone number</Th>
					<Th {table} orderBy="note">Note</Th>
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
					</tr>
				{/each}
			</tbody>
		</table>
	</Datatable>
</div>

{@render children?.()}

<style>
	td {
		text-align: center;
		padding: 4px 0;
	}
</style>
