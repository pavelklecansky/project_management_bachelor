import type {Task} from "../types/core.type"
import {apiRequest, transferify} from "./utils"

export const createTask: (task: Task) => Promise<[Task | null, any]> = async (task) => {
    const [res, error] = await apiRequest({
        endpointName: "tasks",
        method: "POST",
        body: task
    })

    if (error) {
        return [null, error]
    }

    return [res as Task, null]
}

export const getTask: (id: string) => Promise<[Task | null, any]> = async (id) => {
    const endpointName = `tasks/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Task, null]
}

export const getAllTasks: () => Promise<Task[]> = async () => {
    const [res, error] = await apiRequest({
        endpointName: "tasks",
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const getAllUserTasks: () => Promise<Task[]> = async () => {
    const [res, error] = await apiRequest({
        endpointName: "tasks/usersTasks",
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const addComment: (id: string, text: string) => Promise<[Task | null, any]> = async (id, text) => {
    const endpointName = `tasks/${id}/comment`
    const [res, error] = await apiRequest({
        endpointName,
        method: "POST",
        body: {
            text
        }
    })

    if (error) {
        return [null, error]
    }

    return [res as Task, null]
}

export const deleteComment: (id: string, idComment: string) => Promise<[Task | null, any]> = async (id, idComment) => {
    const endpointName = `tasks/${id}/comment/${idComment}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res as Task, null]
}

export const completeTask: (task: Task) => Promise<[Task | null, any]> = async (task) => {
    task.status = "COMPLETED"
    task.progress = 100;

    const [res , error] = await updateTask(transferify(task));

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const updateTask: (task: Task) => Promise<[Task | null, any]> = async (task) => {
    const endpointName = `tasks/${task.id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: task
    })

    if (error) {
        return [null, error]
    }

    return [res as Task, null]
}

export const deleteTask: (id: string) => Promise<[Task | null, any]> = async (id) => {
    const endpointName = `tasks/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}
