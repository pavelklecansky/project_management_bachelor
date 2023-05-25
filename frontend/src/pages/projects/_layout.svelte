<script>
    import {afterPageLoad, url} from "@roxi/routify";

    import CreateButton from "../../components/core/CreateButton.svelte";
    import ProjectList from "../../components/project/ProjectList.svelte";
    import {load} from "../../lib/projects.store";

    $afterPageLoad(() => {
        load();
    });
</script>

<div>
    <div class="flex justify-between m-2">
        <h1 class="text-2xl">Projects</h1>
        <a href={$url("./create")}>
            <CreateButton text={"Create new project"} />
        </a>
    </div>

    <ProjectList allProjects={true} />
</div>

<slot />
