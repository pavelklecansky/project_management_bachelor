<script lang="ts">
    import {goto} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import SubmitButton from "../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../components/core/CloseButton.svelte";
    import EmailInput from "../../../components/core/EmailInput.svelte";
    import TextInput from "../../../components/core/TextInput.svelte";
    import {createForm} from "svelte-forms-lib";
    import ModalWindow from "../../../components/modal/ModalWindow.svelte";
    import TextArea from "../../../components/core/TextArea.svelte";
    import type {Organization} from "../../../types/core.type";
    import {createOrganization} from "../../../lib/organization.service";

    const { form, handleSubmit } = createForm({
		initialValues: {
			organization: {} as Organization,
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await createOrganization(values.organization);
			if (createdError) {
				error(createdError);
				$goto("./../");
			} else {
				success(createdSuccess);
				$goto("./../");
			}
		},
	});
</script>

<ModalWindow outsideClickGotoPath={"./../"} {handleSubmit}>
	<div class="px-4 mb-4 on">
		<h2 class="text-3xl font-medium">Create organization</h2>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Name
			</p>
			<TextInput
				placeholder="Name"
				bind:value={$form.organization.name}
			/>
		</div>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Email
			</p>
			<EmailInput
				placeholder="Email"
				bind:value={$form.organization.email}
			/>
		</div>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				IČO
			</p>
			<TextInput
				placeholder="IČO"
				bind:value={$form.organization.ico}
			/>
		</div>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Phone number
			</p>
			<TextInput
				required={false}
				placeholder="+420123456789"
				bind:value={$form.organization.phoneNumber}
			/>
		</div>

		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Note
			</p>
			<TextArea
				required={false}
				placeholder="Note"
				bind:value={$form.organization.note}
			/>
		</div>
	</div>
	<div
		class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
	>
		<SubmitButton text="Create" />
		<CloseButton text="Close" on:click={() => $goto("./../")} />
	</div>
</ModalWindow>
