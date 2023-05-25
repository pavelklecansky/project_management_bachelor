<script lang="ts">
    import type {User} from "../../../types/authentication.type";
    import {goto, params, ready} from "@roxi/routify";
    import {error, success} from "../../../lib/notification";
    import SubmitButton from "../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../components/core/CloseButton.svelte";
    import EmailInput from "../../../components/core/EmailInput.svelte";
    import TextInput from "../../../components/core/TextInput.svelte";
    import {getUser, updateUser} from "../../../lib/user";
    import {onMount} from "svelte";
    import {createForm} from "svelte-forms-lib";
    import {load} from "../../../lib/users.store";
    import ModalWindow from "../../../components/modal/ModalWindow.svelte";
    import TextArea from "../../../components/core/TextArea.svelte";
    import {isSuperAdmin} from "../../../lib/auth";
    import RolesSelectInput from "../../../components/user/RolesSelectInput.svelte";

    let ready = false;

    const {form, handleSubmit} = createForm({
        initialValues: {
            user: {} as User,
        },

        onSubmit: async (values) => {
            const [editSuccess, editError] = await updateUser(values.user);
            if (editError) {
                error(editError);
                $goto("./../");
            } else {
                success(editSuccess);
                load();
                $goto("./../");
            }
        },
    });

    onMount(async () => {
        let id = $params.id;
        const [success, errorMessage] = await getUser(id);
        if (!success || errorMessage) {
            error(errorMessage);
            $goto("./../");
        } else {
            $form.user = success!;
            ready = true;
        }
    });
</script>

<ModalWindow outsideClickGotoPath={"./../"} {handleSubmit}>
    {#if ready}
        <div class="px-4 mb-4 on">
            <h2 class="text-3xl font-medium">User edit</h2>
            <div class="w-full mt-4 ">
                <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                    Firstname
                </p>
                <TextInput
                        placeholder="Firstname"
                        bind:value={$form.user.firstName}
                />
            </div>

            <div class="w-full mt-4">
                <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                    Lastname
                </p>
                <TextInput
                        placeholder="Lastname"
                        bind:value={$form.user.lastName}
                />
            </div>

            <div class="w-full mt-4">
                <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                    Phone number
                </p>
                <TextInput
                        required={false}
                        placeholder="+420123456789"
                        bind:value={$form.user.phoneNumber}
                />
            </div>

            {#if isSuperAdmin()}
                <div class="w-full mt-4">
                    <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                        Change role
                    </p>
                    <RolesSelectInput value={$form.user}/>
                </div>
            {/if}

            <div class="w-full mt-4">
                <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                    Note
                </p>
                <TextArea
                        required={false}
                        placeholder="Note"
                        bind:value={$form.user.note}
                />
            </div>

            <div class="w-full mt-4">
                <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                    Email
                </p>
                <EmailInput
                        placeholder="Email Address"
                        bind:value={$form.user.email}
                        disabled={true}
                />
            </div>
            <div class="flex items-start w-full mt-4">
                <p class="text-left text-sm font-medium text-gray-900 mr-4">
                    Email verified
                </p>
                <input
                        type="checkbox"
                        class="bg-gray-50 border-gray-300 h-4 w-4 rounded"
                        bind:checked={$form.user.emailVerified}
                        disabled
                />
            </div>
        </div>
        <div
                class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
        >
            <SubmitButton text="Edit"/>
            <CloseButton text="Close" on:click={() => $goto("./../")}/>
        </div>
    {/if}
</ModalWindow>
