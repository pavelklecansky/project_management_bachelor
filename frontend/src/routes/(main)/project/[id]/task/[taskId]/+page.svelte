<script lang="ts">
    import {afterNavigate} from '$app/navigation';
    import {addComment, completeTask, deleteComment, getTask,} from "$lib/task.service";
    import type {Task} from "$lib/types/core.type";
    import DeleteButton from "$lib/components/core/DeleteButton.svelte";
    import EditButton from "$lib/components/core/EditButton.svelte";
    import {getDataFromResponse, priorityLabelFromValue, statusLabelFromValue,} from "$lib/utils";
    import CreateButton from "$lib/components/core/CreateButton.svelte";
    import {error, success} from "$lib/notification";
    import TextArea from "$lib/components/core/TextArea.svelte";
    import SubmitButton from "$lib/components/core/SubmitButton.svelte";
    import {getAvatarUrl} from "$lib/avatar";
    import {isAdmin} from "$lib/auth";
    import TrashCan from "carbon-icons-svelte/lib/TrashCan.svelte";
    import {page} from '$app/stores';

    $: task = {} as Task;
    let loaded = false;
    let commentText = "";

    const taskCompleted = async () => {
        const [successMessage, errorMessage] = await completeTask(task);
        if (!successMessage || errorMessage) {
            error(errorMessage);
        }
        success(successMessage);
        const data = getDataFromResponse(successMessage);
        task = data;
    };

    afterNavigate(async () => {
        let id = $page.params.taskId;
        const [success, errorMessage] = await getTask(id);
        task = success!;
        loaded = true;
    });

    const handleSubmit = async (event) => {
        let id = $page.params.taskId;
        const [successMessage, errorMessage] = await addComment(
            id,
            commentText
        );
        if (errorMessage) {
            error(errorMessage);
        }
        success(successMessage);
        task = getDataFromResponse(successMessage)!;
        commentText = "";
    };

    const deleteCommentOnClick = async (idComment: string) => {
        let id = $page.params.taskId;
        const [successMessage, errorMessage] = await deleteComment(
            id,
            idComment
        );
        if (errorMessage) {
            error(errorMessage);
        }
        success(successMessage);
        task = getDataFromResponse(successMessage)!;
        commentText = "";
    };
</script>

<div>
    {#if loaded}
        <div>
            <h1 class="text-4xl font-bold">{task.name}</h1>
            <div class="flex justify-between gap-4">
                <div class="w-4/5">
                    <div class="mt-2">
                        <h2 class="text-xl">Description</h2>
                        <p>
                            {task.description}
                        </p>
                    </div>
                    <div class="mt-2">
                        <h2 class="text-xl">Status</h2>
                        <p class="text-lg">
                            {statusLabelFromValue(task.status)}
                        </p>
                    </div>
                    {#if task.assigned}
                        <div class="mt-2">
                            <h2 class="text-xl">Assigneed</h2>
                            <p>
                                {`${task.assigned.firstName} ${task.assigned.lastName}`}
                            </p>
                        </div>
                    {/if}

                    {#if task.assignedForGroup}
                        <div class="mt-2">
                            <h2 class="text-xl">Assigneed for Group</h2>
                            <p>
                                {task.assignedForGroup.name}
                            </p>
                        </div>
                    {/if}

                    {#if task.phase}
                        <div class="mt-2">
                            <h2 class="text-xl">Phase</h2>
                            <p>
                                {task.phase.name}
                            </p>
                        </div>
                    {/if}

                    <div class="mt-2">
                        <h2 class="text-xl">Priority</h2>
                        <p class="text-lg">
                            {priorityLabelFromValue(task.priority)}
                        </p>
                    </div>
                    <div class="mt-2">
                        <h2 class="text-xl">Progress</h2>
                        <div class="w-full bg-gray-200 rounded-full">
                            <div
                                class="bg-blue-600 text-xs font-medium text-blue-100 text-center p-0.5 leading-none rounded-full"
                                style="width: {task.progress}%"
                            >
                                {task.progress}%
                            </div>
                        </div>
                    </div>
                    <div class="mt-2">
                        <hr />
                        <p class="mt-2">
                            <span class="font-bold">Start date:</span>
                            {new Date(task.startDate).toLocaleDateString("cs")}
                        </p>
                        <p>
                            <span class="font-bold">End date:</span>
                            {new Date(task.endDate).toLocaleDateString("cs")}
                        </p>
                    </div>
                </div>
                <div class="w-1/5 flex flex-col text-center">
                    <h3 class="text-1xl font-bold">Actions</h3>
                    <a class="mt-2" href={`./delete/${task.id}`}
                        ><DeleteButton text={"Delete task"} full={true} /></a
                    >
                    <a class="mt-2" href={`./edit/${task.id}`}
                        ><EditButton text={"Edit task"} full={true} /></a
                    >
                    <div class="mt-2">
                        <CreateButton
                            text={"Complete task"}
                            full={true}
                            on:click={() => taskCompleted()}
                        />
                    </div>
                </div>
            </div>
        </div>
        <div class="mt-2">
            <h2 class="text-3xl font-bold">Comments</h2>
            <div class="mt-2">
                <form on:submit|preventDefault={handleSubmit}>
                    <TextArea
                        placeholder="Add comment"
                        bind:value={commentText}
                    />
                    <div class="flex justify-end mt-2">
                        <SubmitButton text="Add new comment" />
                    </div>

                    {#each task.comments as comment}
                        <div
                            class="rounded-lg p-3 flex flex-col shadow-lg mb-4 relative"
                        >
                            <div class="flex justify-between mr-2">
                                <div class="flex flex-row">
                                    <img
                                        class="w-8 h-8 mr-3 rounded-full object-cover"
                                        src={getAvatarUrl(
                                            comment.user.firstName,
                                            comment.user.lastName
                                        )}
                                        alt="Use avatar"
                                    />
                                    <h3
                                        class=" font-semibold text-lg text-left "
                                    >
                                        {comment.user.firstName}
                                        {comment.user.lastName}
                                    </h3>
                                </div>
                                <div>
                                    {new Date(
                                        comment.createdDate
                                    ).toLocaleDateString("cs")}
                                </div>
                            </div>

                            <div class="flex justify-between">
                                <p
                                    style="width: 90%"
                                    class="text-gray-600 text-lg text-left "
                                >
                                    {comment.text}
                                </p>
                                {#if isAdmin()}
                                    <div class="text-red-500">
                                        <TrashCan
                                            on:click={() =>
                                                deleteCommentOnClick(
                                                    comment.id
                                                )}
                                        />
                                    </div>
                                {/if}
                            </div>
                        </div>
                    {/each}
                </form>
            </div>
        </div>
    {/if}
</div>
