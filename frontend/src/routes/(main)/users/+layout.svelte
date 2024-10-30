<script lang="ts">
	import { users } from '$lib/users.store';
	import { Datatable, TableHandler, Th, ThFilter } from '@vincjo/datatables';
	import { EditIcon, Trash2Icon } from 'svelte-feather-icons';
	import CreateButton from '$lib/components/core/CreateButton.svelte';
	import PasscodePopup from '$lib/components/user/PasscodePopup.svelte';
	import { getContext } from 'svelte';
	import { generatePasscode } from '$lib/user';
	import { error } from '$lib/notification';
	import { getDataFromResponse } from '$lib/utils';
	import type { User } from '$lib/types/authentication.type';

	interface Props {
		children?: import('svelte').Snippet;
	}

	let { children }: Props = $props();

	const { open } = getContext('simple-modal');

	let table = new TableHandler<User>($users, { rowsPerPage: 50 });

	const generatePasscodeOnClick = async () => {
		const [generateSuccess, generateError] = await generatePasscode();
		if (generateError) {
			error(generateError);
		} else {
			open(PasscodePopup, { message: getDataFromResponse(generateSuccess).passcode });
		}
	};
</script>

<div class="flex justify-end w-full mb-2">
	<CreateButton text={'Generate passcode'} on:click={generatePasscodeOnClick} />
</div>

<Datatable {table} pagination={true}>
	<table>
		<thead>
			<tr>
				<Th {table} orderBy="firstName">First name</Th>
				<Th {table} orderBy="lastName">Last name</Th>
				<Th {table} orderBy="email">Email</Th>
				<Th {table} orderBy="roles">Roles</Th>
				<th>Actions</th>
			</tr>
			<tr>
				<ThFilter {table} filterBy="firstName" />
				<ThFilter {table} filterBy="lastName" />
				<ThFilter {table} filterBy="email" />
				<ThFilter {table} filterBy="roles" />
			</tr>
		</thead>
		<tbody>
			{#each table.rows as row}
				<tr>
					<td>{row.firstName}</td>
					<td>{row.lastName}</td>
					<td>{row.email}</td>
					<td>{row.roles}</td>
					<td class="flex justify-center">
						<a href={`./edit/${row.id}`}>
							<EditIcon size="1.5x" />
						</a>
						<a class="mr-4" href={`./delete/${row.id}`}>
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
