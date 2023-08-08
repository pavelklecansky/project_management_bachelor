<script>
    import {afterNavigate} from '$app/navigation';

    import CreateButton from "$lib/components/core/CreateButton.svelte";
    import ProjectList from "$lib/components/project/ProjectList.svelte";
    import {load} from "$lib/projects.store";

    afterNavigate(() => {
        load();
    });
</script>

<div>
    <div class="flex justify-between m-2">
        <h1 class="text-2xl">Projects</h1>
        <a href={"/projects/create"}>
            <CreateButton text={"Create new project"} />
        </a>
    </div>

    <ProjectList allProjects={true} />
</div>

<slot />
