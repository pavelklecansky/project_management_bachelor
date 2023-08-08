<script lang="ts">
    import PasswordInput from "$lib/components/core/PasswordInput.svelte";
    import SubmitButton from "$lib/components/core/SubmitButton.svelte";
    import {createForm} from "svelte-forms-lib";
    import {error, success} from "$lib/notification";
    import {changePassword} from "$lib/user";


    const { form, errors, handleSubmit } = createForm({
        initialValues: {
            currentPassword: "",
            newPassword: "",
            confirmPassword: "",
        },

        validate: (values) => {
            let errors: any = {};
            if (!values.confirmPassword) {
                errors["confirmPassword"] = "Confirm password is required.";
            } else if (values.confirmPassword !== values.newPassword) {
                errors["confirmPassword"] = "Passwords does not match.";
            }
            return errors;
        },

        onSubmit: async (values) => {
            const [successResponse, errorResponse] = await changePassword(
                values.currentPassword,
                values.newPassword
            );
            if (errorResponse) {
                error(errorResponse);
                $form.confirmPassword = "";
                $form.currentPassword = "";
                $form.newPassword = "";
            } else {
                success(successResponse);
                $form.confirmPassword = "";
                $form.currentPassword = "";
                $form.newPassword = "";
            }
        },
    });

    $: if ($errors.confirmPassword) {
        error($errors.confirmPassword)
    }
</script>

<div class="bg-white border border-gray-100 rounded">
    <header class="flex items-stretch border-b border-gray-100">
        <p class="flex items-center py-3 px-4 flex-grow font-bold">
            Change Password
        </p>
    </header>
    <div class="p-6">
        <form on:submit|preventDefault={handleSubmit}>
            <div class="mb-3">
                <p class="block font-bold mb-2">Current password</p>
                <div class="control">
                    <PasswordInput
                        placeholder="Current password"
                        bind:value={$form.currentPassword}
                    />
                </div>
            </div>
            <hr />
            <div class="mb-3">
                <p class="block font-bold mb-2">New password</p>
                <div class="control">
                    <PasswordInput
                        placeholder="New password"
                        bind:value={$form.newPassword}
                    />
                </div>
            </div>
            <div class="mb-3">
                <p class="block font-bold mb-2">Confirm password</p>
                <div class="control">
                    <PasswordInput placeholder="Confirm password" bind:value={$form.confirmPassword} />
                </div>
            </div>
            <div class="mt-3">
                <SubmitButton text="Submit" />
            </div>
        </form>
    </div>
</div>
