<script lang="ts">
	import { page } from '$app/stores';
	import { afterNavigate, goto } from '$app/navigation';
	import { error, success } from '$lib/notification';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import CloseButton from '$lib/components/core/CloseButton.svelte';
	import EmailInput from '$lib/components/core/EmailInput.svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import { onMount } from 'svelte';
	import { createForm } from 'svelte-forms-lib';
	import ModalWindow from '$lib/components/modal/ModalWindow.svelte';
	import TextArea from '$lib/components/core/TextArea.svelte';
	import type { Organization } from '$lib/types/core.type';
	import { getOrganization, updateOrganization } from '$lib/organization.service';
	import { base } from '$app/paths';

	let ready = false;

	let previousPage: string = '/organizations';

	afterNavigate(({ from }) => {
		previousPage = from?.url.pathname || previousPage;
	});

	const { form, handleSubmit } = createForm({
		initialValues: {
			organization: {} as Organization
		},

		onSubmit: async (values) => {
			const [editSuccess, editError] = await updateOrganization(values.organization);
			if (editError) {
				error(editError);
				await goto(previousPage);
			} else {
				success(editSuccess);
				await goto(previousPage);
			}
		}
	});

	onMount(async () => {
		let id = $page.params.id;
		const [success, errorMessage] = await getOrganization(id);
		if (!success || errorMessage) {
			error(errorMessage);
			goto(previousPage);
		} else {
			$form.organization = success!;
			ready = true;
		}
	});
</script>

{#if ready}
	<ModalWindow outsideClickGotoPath={previousPage} {handleSubmit}>
		<div class="px-4 mb-4 on">
			<h2 class="text-3xl font-medium">Organization edit</h2>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Name</p>
				<TextInput placeholder="Name" bind:value={$form.organization.name} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Email</p>
				<EmailInput placeholder="Email" bind:value={$form.organization.email} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">IČO</p>
				<TextInput placeholder="IČO" bind:value={$form.organization.ico} />
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Phone number</p>
				<TextInput
					required={false}
					placeholder="+420123456789"
					bind:value={$form.organization.phoneNumber}
				/>
			</div>

			<div class="w-full mt-4">
				<p class="text-left text-sm font-medium text-gray-900 block mb-2">Note</p>
				<TextArea required={false} placeholder="Note" bind:value={$form.organization.note} />
			</div>
		</div>
		<div class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false">
			<SubmitButton text="Edit" />
			<CloseButton text="Close" on:click={() => goto(previousPage)} />
		</div>
	</ModalWindow>
{/if}
