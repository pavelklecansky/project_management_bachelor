<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../lib/notification";
    import ModalWindow from "../../../../components/modal/ModalWindow.svelte";
    import SubmitButton from "../../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../../components/core/CloseButton.svelte";
    import {createForm} from "svelte-forms-lib";
    import {addGroupMember} from "../../../../lib/project.service";
    import type {Group} from "../../../../types/core.type";
    import GroupSelectInput from "../../../../components/group/GroupSelectInput.svelte";

    let id = $params.id;

    const { form, handleSubmit } = createForm({
        initialValues: {
            member: {} as Group,
        },

        onSubmit: async (values) => {
            const [createdSuccess, createdError] = await addGroupMember(
                id,
                values.member
            );
            if (createdError) {
                error(createdError);
                $goto(`../${id}`);
            } else {
                success(createdSuccess);
                $goto(`../${id}`);
            }
        },
    });
</script>

<ModalWindow outsideClickGotoPath={`../${id}`} {handleSubmit}>
    <div class="px-4 mb-4">
        <h2 class="text-3xl font-medium">Add new group member</h2>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Member
            </p>

            <GroupSelectInput bind:value={$form.member} />
        </div>
    </div>
    <div
        class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
    >
        <SubmitButton text="Create" />
        <CloseButton text="Close" on:click={() => $goto(`../${id}`)} />
    </div>
</ModalWindow>
