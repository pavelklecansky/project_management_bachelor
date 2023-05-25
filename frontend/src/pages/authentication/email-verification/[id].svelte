<script lang="ts">
    import {goto, params, ready} from "@roxi/routify";
    import {onMount} from "svelte";
    import {apiRequest} from "../../../lib/utils";
    import {CheckCircleIcon} from "svelte-feather-icons";

    onMount(async () => {
        let endpointName = `users/register/token/${$params.id}`;
        const [res, errorMessage] = await apiRequest({
            endpointName,
            method: "GET",
        });

        if (!res || errorMessage) {
            $goto("../error");
        }
        $ready();
    });
</script>

<div class="flex flex-col">
    <h2 class="text-6xl font-bold text-center text-gray-700">
        Email was verified
    </h2>
    <CheckCircleIcon size="6x" class="text-green-600 self-center mt-4"/>
</div>
