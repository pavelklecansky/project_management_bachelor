<script lang="ts">
    import {afterPageLoad, params, url} from "@roxi/routify";
    import {addComment, deleteComment, getProject,} from "../../lib/project.service";
    import type {Project} from "../../types/core.type";
    import DeleteButton from "../../components/core/DeleteButton.svelte";
    import EditButton from "../../components/core/EditButton.svelte";
    import CreateButton from "../../components/core/CreateButton.svelte";
    import TaskList from "../../components/task/TaskList.svelte";
    import TextArea from "../../components/core/TextArea.svelte";
    import SubmitButton from "../../components/core/SubmitButton.svelte";
    import {getAvatarUrl} from "../../lib/avatar";
    import {error, success} from "../../lib/notification";
    import {getDataFromResponse} from "../../lib/utils";
    import {isAdmin} from "../../lib/auth";
    import Delete24 from "carbon-icons-svelte/lib/Delete24";

    let project = {} as Project;
    let loaded = false;
    let commentText = "";

    $afterPageLoad(async () => {
        let id = $params.id;
        const [success, errorMessage] = await getProject(id);
        project = success!;
        loaded = true;
    });

    const handleSubmit = async (event) => {
        let id = $params.id;
        const [successMessage, errorMessage] = await addComment(
            id,
            commentText
        );
        if (errorMessage) {
            error(errorMessage);
        }
        success(successMessage);
        project = getDataFromResponse(successMessage)!;
        commentText = "";
    };

    const deleteCommentOnClick = async (idComment: string) => {
        let id = $params.id;
        const [successMessage, errorMessage] = await deleteComment(
            id,
            idComment
        );
        if (errorMessage) {
            error(errorMessage);
        }
        success(successMessage);
        project = getDataFromResponse(successMessage)!;
        commentText = "";
    };
</script>

<div>
    {#if loaded}
        <div>
            <h1 class="text-4xl font-bold">{project.name}</h1>
            <div class="flex justify-between gap-4">
                <div class="w-4/5">
                    <div class="mt-2">
                        <h2 class="text-xl">Description</h2>
                        <p>
                            {project.description}
                        </p>
                    </div>
                    <div class="mt-2">
                        <hr />
                        <p class="mt-2">
                            <span class="font-bold">Start date:</span>
                            {new Date(project.startDate).toLocaleDateString(
                                "cs"
                            )}
                        </p>
                        <p>
                            <span class="font-bold">End date:</span>
                            {new Date(project.endDate).toLocaleDateString("cs")}
                        </p>
                    </div>
                </div>
                <div class="w-1/5 flex flex-col text-center">
                    <h3 class="text-1xl font-bold">Actions</h3>
                    <a class="mt-2" href={$url(`./delete/${project.id}`)}
                        ><DeleteButton text={"Delete project"} full={true} /></a
                    >
                    <a class="mt-2" href={$url(`./edit/${project.id}`)}
                        ><EditButton text={"Edit project"} full={true} /></a
                    >
                    <a class="mt-2" href={$url(`./create-task/${project.id}`)}
                        ><CreateButton text={"New task"} full={true} /></a
                    >
                </div>
            </div>
        </div>
        <div class="mt-2">
            <hr />
            <h2 class="text-3xl font-bold">Tasks</h2>
            <div class="mt-2">
                <TaskList tasks={project.tasks} allTasks={true} />
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

                    {#each project.comments as comment}
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
                                        <Delete24
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
