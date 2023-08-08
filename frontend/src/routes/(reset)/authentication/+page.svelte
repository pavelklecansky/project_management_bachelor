<script lang="ts">
    import {signIn} from "$lib/auth";
    import {error} from "$lib/notification";
    import { goto } from '$app/navigation';
    import PasswordInput from "$lib/components/core/PasswordInput.svelte";
    import EmailInput from "$lib/components/core/EmailInput.svelte";
    import SubmitButton from "$lib/components/core/SubmitButton.svelte";

    let email = "";
    let password = "";
    const handleSubmit = async () => {
        const [_, errorMessage] = await signIn(email, password);
        console.log(errorMessage);
        if (errorMessage) {
            error(errorMessage);
        } else {
            goto("/");
        }
    };
</script>

<div>
    <h2 class="text-3xl font-bold text-center text-gray-700 dark:text-white">
        Welcome Back
    </h2>

    <p class="mt-1 text-center text-gray-500 dark:text-gray-400">
        Login or create account
    </p>

    <form on:submit|preventDefault={handleSubmit}>
        <div class="w-full mt-4">
            <EmailInput placeholder="Email Address" bind:value={email} />
        </div>

        <div class="w-full mt-4">
            <PasswordInput placeholder="Password" bind:value={password} />
        </div>

        <div class="flex items-center justify-between mt-4 mb-2">
            <a
                href={"./passwordReset"}
                class="text-sm text-gray-600 dark:text-gray-200 hover:text-gray-500"
                >Forget Password?</a
            >

            <SubmitButton text="Login" />
        </div>
    </form>

    <div
        class="flex items-center justify-center py-4 text-center bg-gray-50 dark:bg-gray-700"
    >
        <span class="text-sm text-gray-600 dark:text-gray-200"
            >Don't have an account?
        </span>

        <a
            href={"./register"}
            class="mx-2 text-sm font-bold text-blue-500 dark:text-blue-400 hover:underline"
            >Register</a
        >
    </div>
</div>
