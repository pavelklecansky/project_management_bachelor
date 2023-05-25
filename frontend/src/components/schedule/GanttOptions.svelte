<script>
    import {createEventDispatcher, getContext} from "svelte";

    export let options;

    $: {
        dispatch('change', options);
    }

    const dispatch = createEventDispatcher();

    let {optionsStream} = getContext('options');
    $: {
        dispatch('change', $optionsStream);
    }
</script>
