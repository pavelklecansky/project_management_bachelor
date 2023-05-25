<!-- routify:options query-params-is-page -->
<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {onMount} from "svelte";
    import {downloadFileInBrowser} from "../../../../../lib/storage.service";


    const urlSearchParams = new URLSearchParams(window.location.search);
    let id = $params.id;

    $: queryParams = urlSearchParams.get('path') || "";
    $: queryParamsName = urlSearchParams.get('name') || "";

    onMount(async () => {
        await downloadFileInBrowser(id, queryParamsName, queryParams);
        $goto(`/project/files/${id}?path=${queryParams}`);
    });
</script>

