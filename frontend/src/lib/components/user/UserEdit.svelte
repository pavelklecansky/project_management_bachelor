<script lang="ts">
	import TextInput from '$lib/components/core/TextInput.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import { authState, updateLoginUser } from '$lib/auth';
	import TextArea from '$lib/components/core/TextArea.svelte';
	import OrganizationSelectInput from '$lib/components/organization/OrganizationSelectInput.svelte';
	import { error, success } from '$lib/notification';

	const user = $authState.user;

	const handleSubmit = async () => {
		const [editSuccess, editError] = updateLoginUser(user);
		if (editError) {
			error(editError);
		} else {
			success(editSuccess);
		}
	};
</script>

<div class="bg-white border border-gray-100 rounded">
	<header class="flex items-stretch border-b border-gray-100">
		<p class="flex items-center py-3 px-4 flex-grow font-bold">Edit Profile</p>
	</header>
	<div class="p-6">
		<form on:submit|preventDefault={handleSubmit}>
			<div class="mb-3">
				<p class="block font-bold mb-2">First name</p>
				<div class="field-body">
					<div class="mb-3">
						<div class="control">
							<TextInput placeholder="Firstname" bind:value={user.firstName} />
						</div>
					</div>
				</div>
			</div>
			<div class="mb-3">
				<p class="block font-bold mb-2">Last name</p>
				<div class="field-body">
					<div class="mb-3">
						<div class="control">
							<TextInput placeholder="Lastname" bind:value={user.lastName} />
						</div>
					</div>
				</div>
			</div>
			<div class="mb-3">
				<p class="block font-bold mb-2">Phone number</p>
				<div class="field-body">
					<div class="mb-3">
						<div class="control">
							<TextInput
								required={false}
								placeholder="+420123456789"
								bind:value={user.phoneNumber}
							/>
						</div>
					</div>
				</div>
			</div>
			<div class="mb-3">
				<p class="block font-bold mb-2">Organizations</p>
				<div class="field-body">
					<div class="mb-3">
						<div class="control">
							<OrganizationSelectInput value={user} />
						</div>
					</div>
				</div>
			</div>
			<div class="mb-3">
				<p class="block font-bold mb-2">Note</p>
				<div class="field-body">
					<div class="mb-3">
						<div class="control">
							<TextArea required={false} placeholder="Note" bind:value={user.note} />
						</div>
					</div>
				</div>
			</div>
			<div class="mb-3">
				<div class="control">
					<SubmitButton text="Submit" />
				</div>
			</div>
		</form>
	</div>
</div>
