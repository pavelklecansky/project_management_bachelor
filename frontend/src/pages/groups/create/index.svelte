<script lang="ts">
    import ModalWindow from "../../../components/modal/ModalWindow.svelte";
    import type {Group, GroupMember} from "../../../types/core.type";
    import {createForm} from "svelte-forms-lib";
    import TextInput from "../../../components/core/TextInput.svelte";
    import UserSelectInput from "../../../components/user/UserSelectInput.svelte";
    import SubmitButton from "../../../components/core/SubmitButton.svelte";
    import {Trash2Icon} from "svelte-feather-icons";
    import {goto} from "@roxi/routify";
    import CloseButton from "../../../components/core/CloseButton.svelte";
    import {createGroup} from "../../../lib/groups.service";
    import {error, success} from "../../../lib/notification";

    const { form, handleSubmit } = createForm({
		initialValues: {
			group: {
				members: [],
			} as Group,
		},

		onSubmit: async (values) => {
			const [createdSuccess, createdError] = await createGroup(
				values.group
			);
			if (createdError) {
				error(createdError);
				$goto("./../");
			} else {
				success(createdSuccess);
				$goto("./../");
			}
		},
	});

	const { form: addNewMemberForm, handleSubmit: addNewMemberHandle } =
		createForm({
			initialValues: {
				groupMember: {} as GroupMember,
			},

			onSubmit: async (values) => {
				$form.group.members = [
					...$form.group.members,
					JSON.parse(JSON.stringify(values.groupMember)),
				];
				$addNewMemberForm.groupMember.position = "";
			},
		});

	const removeMemberFromArray = (userId: string) => {
		$form.group.members = $form.group.members.filter(
			(member) => member.user.id !== userId
		);
	};
</script>

<ModalWindow outsideClickGotoPath={`./../`} {handleSubmit}>
	<div class="px-4 mb-4">
		<h2 class="text-3xl font-medium">Create new group</h2>
		<div class="w-full mt-4">
			<p class="text-left text-sm font-medium text-gray-900 block mb-2">
				Name
			</p>
			<TextInput placeholder="Name" bind:value={$form.group.name} />
		</div>
		<div>
			{#if $form.group.members.length > 0}
				<div class="w-full mt-4">
					<div class="text-lg text-gray-900 mb-2 text-left">
						<p class="text-left font-medium">Members</p>
						<div class="border-b border-gray-200 shadow mt-1">
							<table class="w-full">
								<thead class="bg-gray-50">
									<tr>
										<th class="px-6 py-2">User</th>
										<th class="px-6 py-2">Position</th>
										<th class="px-6 py-2">Action</th>
									</tr>
								</thead>
								<tbody class="bg-white">
									{#each $form.group.members as member}
										<tr class="whitespace-nowrap">
											<td class="px-6 py-4">
												{member.user.firstName}
												{member.user.lastName}
											</td>
											<td class="px-6 py-4"
												>{member.position}</td
											>
											<td
												class="px-6 py-4 text-red-500"
												on:click={() =>
													removeMemberFromArray(
														member.user.id
													)}
												><Trash2Icon size="1.5x" /></td
											>
										</tr>
									{/each}
								</tbody>
							</table>
						</div>
					</div>
				</div>
			{/if}
			<div class="w-full mt-4">
				<p
					class="text-left text-sm font-medium text-gray-900 block mb-2"
				>
					Add member
				</p>
				<form on:submit|preventDefault={addNewMemberHandle}>
					<UserSelectInput
						bind:value={$addNewMemberForm.groupMember.user}
					/>
					<TextInput
						placeholder="Position"
						bind:value={$addNewMemberForm.groupMember.position}
					/>
					<div class="mt-2">
						<SubmitButton text={"Add new member"} full={true} />
					</div>
				</form>
			</div>
		</div>
		<div
			class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
		>
			<SubmitButton text="Create" />
			<CloseButton text="Close" on:click={() => $goto(`./../`)} />
		</div>
	</div>
</ModalWindow>
