
<script lang="ts">
    import { page } from '$app/stores';
    import { goto } from '$app/navigation';

    import {onMount} from "svelte";
    import {downloadFileInBrowser} from "$lib/storage.service";


    const urlSearchParams = new URLSearchParams(window.location.search);
    let id = $page.params.id;

    $: queryParams = urlSearchParams.get('path') || "";
    $: queryParamsName = urlSearchParams.get('name') || "";

    onMount(async () => {
        await downloadFileInBrowser(id, queryParamsName, queryParams);
        goto(`/project/files/${id}?path=${queryParams}`);
    });
</script>

