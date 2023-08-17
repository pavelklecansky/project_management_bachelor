<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import type { User } from '$lib/types/authentication.type';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import UserSelectInput from '$lib/components/user/UserSelectInput.svelte';
	import { createForm } from 'svelte-forms-lib';
	import { addMember } from '$lib/project.service';

	let id = $page.params.id;

	const { form, handleSubmit } = createForm({
		initialValues: {
			member: {} as User
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await addMember(id, values.member);
			if (createdError) {
				error(createdError);
				goto(`../${id}`);
			} else {
				success(createdSuccess);
				goto(`../${id}`);
			}
		}
	});
</script>

<ModalWindow outsideClickGotoPath={`../${id}`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Add new member</h2>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">Member</p>

			<UserSelectInput bind:value={$form.member} />
		</div>
	</div>
	<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => goto(`../${id}`)} />
	</div>
</ModalWindow>
