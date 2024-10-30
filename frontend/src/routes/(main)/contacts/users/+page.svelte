<script>
	import { users } from '$lib/users.store';
	import { Datatable, TableHandler, Th, ThFilter } from '@vincjo/datatables';

	let table = new TableHandler($users, { rowsPerPage: 50 });
</script>

<Datatable {table} pagination={true}>
	<table>
		<thead>
			<tr>
				<Th {table} orderBy="firstName">First name</Th>
				<Th {table} orderBy="lastName">Last name</Th>
				<Th {table} orderBy="email">Email</Th>
				<Th {table} orderBy="phoneNumber">Phone number</Th>
				<Th {table} orderBy="note">Note</Th>
			</tr>
			<tr>
				<ThFilter {table} filterBy="firstName" />
				<ThFilter {table} filterBy="lastName" />
				<ThFilter {table} filterBy="email" />
				<ThFilter {table} filterBy="phoneNumber" />
				<ThFilter {table} filterBy="note" />
			</tr>
		</thead>
		<tbody>
			{#each table.rows as row}
				<tr>
					<td>{row.firstName}</td>
					<td>{row.lastName}</td>
					<td>{row.email}</td>
					<td>{row.phoneNumber}</td>
					<td>{row.note}</td>
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
