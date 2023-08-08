<script lang="ts">
    import {page} from '$app/stores';
    import {goto} from '$app/navigation';
    import {onMount} from "svelte";
    import {apiRequest} from "$lib/utils";
    import {CheckCircleIcon} from "svelte-feather-icons";

    let ready = false;

    onMount(async () => {
        let endpointName = `users/register/token/${$page.params.id}`;
        const [res, errorMessage] = await apiRequest({
            endpointName,
            method: "GET",
        });

        if (!res || errorMessage) {
            goto("../error");
        }
        ready = true;
    });
</script>

{#if ready}
    <div class="flex flex-col">
        <h2 class="text-6xl font-bold text-center text-gray-700">
            Email was verified
        </h2>
        <CheckCircleIcon size="6x" class="text-green-600 self-center mt-4"/>
    </div>
{/if}
