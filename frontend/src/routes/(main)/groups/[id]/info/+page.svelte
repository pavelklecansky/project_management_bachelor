<script lang="ts">
	import { page } from '$app/stores';
	import { afterNavigate, goto } from '$app/navigation';
	import { error } from '$lib/notification';
	import { onMount } from 'svelte';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import { getGroup } from '$lib/groups.service';
	import type { Group } from '$lib/types/core.type';
	import { getDataFromResponse } from '$lib/utils';
	import { base } from '$app/paths';

	let group = {} as Group;
	let loaded = false;

	let previousPage: string = base;

	afterNavigate(({ from }) => {
		previousPage = from?.url.pathname || previousPage;
	});

	onMount(async () => {
		let id = $page.params.id;
		const [success, errorMessage] = await getGroup(id);
		if (!success || errorMessage) {
			error(errorMessage);
			goto(previousPage);
		}
		group = getDataFromResponse(success);
		loaded = true;
	});
</script>

<ModalWindow outsideClickGotoPath={previousPage}>
	{#if loaded}
		<div class="px-4 mb-4">
			<h2 class="text-3xl font-medium">Group info</h2>
			<div class="w-full mt-4">
				<div class="text-lg text-gray-900 mb-2 text-left">
					<p>
						<span class="text-left font-medium">Name:</span>
						{group.name}
					</p>
				</div>
			</div>
			<div class="w-full mt-4">
				<div class="text-lg text-gray-900 mb-2 text-left">
					<p class="text-left font-medium">Members</p>
					<div class="border-b border-gray-200 shadow mt-1">
						<table class="w-full">
							<thead class="bg-gray-50">
								<tr>
									<th class="px-6 py-2">User</th>
									<th class="px-6 py-2">Position</th>
								</tr>
							</thead>
							<tbody class="bg-white">
								{#each group.members as member}
									<tr class="whitespace-nowrap">
										<td class="px-6 py-4">
											{member.user.firstName}
											{member.user.lastName}</td
										>
										<td class="px-6 py-4">{member.position}</td>
									</tr>
								{/each}
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	{/if}
</ModalWindow>
