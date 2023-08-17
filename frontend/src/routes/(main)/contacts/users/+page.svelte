<script>
	import { users } from '$lib/users.store';
	import { Datatable, DataHandler, Th, ThFilter } from '@vincjo/datatables';
	import { afterNavigate } from '$app/navigation';

	let handler;
	let rows;

	afterNavigate(async () => {
		handler = new DataHandler($users, { rowsPerPage: 50 });
		rows = handler.getRows();
	});
</script>

{#if rows}
	<Datatable {handler} pagination={true}>
		<table>
			<thead>
				<tr>
					<Th {handler} orderBy="firstName">First name</Th>
					<Th {handler} orderBy="lastName">Last name</Th>
					<Th {handler} orderBy="email">Email</Th>
					<Th {handler} orderBy="phoneNumber">Phone number</Th>
					<Th {handler} orderBy="note">Note</Th>
				</tr>
				<tr>
					<ThFilter {handler} filterBy="firstName" />
					<ThFilter {handler} filterBy="lastName" />
					<ThFilter {handler} filterBy="email" />
					<ThFilter {handler} filterBy="phoneNumber" />
					<ThFilter {handler} filterBy="note" />
				</tr>
			</thead>
			<tbody>
				{#each $rows as row}
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
{/if}

<style>
	td {
		text-align: center;
		padding: 4px 0;
	}
</style>
