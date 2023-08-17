<script lang="ts">
	import type { BudgetItem } from '$lib/types/core.type';
	import { EditIcon, Trash2Icon } from 'svelte-feather-icons';

	export let item: BudgetItem;
	export let id;
</script>

<div class="ml-10">
	<div class="flex justify-between">
		<h2 class="text-lg font-medium">{item.label}</h2>
		<div class="text-base font-medium flex items-center">
			<span class="text-red-600 mr-2">{item.budget},-</span>
			<div class="flex justify-center">
				<a href={`/project/${id}/budget/${item.id}/edit-item`}>
					<EditIcon size="1x" />
				</a>
				<a href={`/project/${id}/budget/${item.id}/delete-item`}>
					<Trash2Icon size="1x" />
				</a>
			</div>
		</div>
	</div>
</div>
