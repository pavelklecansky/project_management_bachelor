<script lang="ts">
    import {Router} from '@roxi/routify';
    import {routes} from "../.routify/routes";
    import {SvelteToast} from "@zerodevx/svelte-toast";
    import {Modal} from "svelte-simple-modal";
    import "./global.css";
</script>

<SvelteToast/>

<Modal>
    <Router {routes}/>
</Modal>

<style global>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
</style>
