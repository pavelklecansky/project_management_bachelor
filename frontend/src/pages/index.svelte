<script>
    import {onMount} from "svelte";

    import ProjectList from "../components/project/ProjectList.svelte";
    import TaskList from "../components/task/TaskList.svelte";
    import {getAllUserTasks} from "../lib/task.service";

    $: tasks = [];

    onMount(async () => {
        const [success, error] = await getAllUserTasks();
        tasks=success;
    });
</script>

<div>
    <div>
        <h1 class="text-2xl">My projects</h1>
        <ProjectList class="mt-2"/>
    </div>
    <div class="mt-6">
        <h1 class="text-2xl">My tasks</h1>
        <div class="mt-2">
            <TaskList {tasks}/>
        </div>
    </div>
</div>
