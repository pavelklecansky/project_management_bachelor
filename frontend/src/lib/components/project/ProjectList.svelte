<script lang="ts">
    import ProjectCard from "./ProjectCard.svelte";
    import {projects} from "$lib/projects.store";

    export let allProjects = false;

    $: data = $projects;

    $: if (!allProjects) {
        data = data.slice(0, 3);
    }
</script>

<div class="flex md:justify-start justify-center flex-wrap gap-5">
    {#each data as project}
        <ProjectCard {project} />
    {/each}
</div>
