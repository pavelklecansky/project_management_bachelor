<script lang="ts">
    import {apiRequest} from "../../lib/utils";
    import {error, success} from "../../lib/notification";
    import {goto, url} from "@roxi/routify";
    import EmailInput from "../../components/core/EmailInput.svelte";
    import SubmitButton from "../../components/core/SubmitButton.svelte";

    let email = "";

    const handleSubmit = async () => {
        const [_, errorMessage] = await apiRequest({
            endpointName: "users/passwordReset",
            method: "POST",
            body: { email },
        });
        if (errorMessage) {
            error(errorMessage);
        } else {
            success(_);
            $goto("./index");
        }
    };
</script>

<div>
    <h2 class="text-3xl font-bold text-center text-gray-700 dark:text-white">
        Forgotten Password?
    </h2>

    <p class="mt-1 text-center text-gray-500 dark:text-gray-400">
        Please enter your login email address.
    </p>

    <form on:submit|preventDefault={handleSubmit}>
        <div class="w-full mt-4">
            <EmailInput placeholder="Email Address" bind:value={email} />
        </div>

        <div class="flex items-center justify-between mt-4 mb-2">
            <span
                class="text-sm text-gray-600 dark:text-gray-200 hover:text-gray-500"
            />
            <SubmitButton text="Continue" />
        </div>
    </form>

    <div
        class="flex items-center justify-center py-4 text-center bg-gray-50 dark:bg-gray-700"
    >
        <span class="text-sm text-gray-600 dark:text-gray-200"
            >Don't have an account?
        </span>

        <a
            href={$url("./register")}
            class="mx-2 text-sm font-bold text-blue-500 dark:text-blue-400 hover:underline"
            >Register</a
        >
    </div>
</div>
