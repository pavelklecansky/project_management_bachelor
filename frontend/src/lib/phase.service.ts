import type {Phase, Task} from "$lib/types/core.type"
import {apiRequest} from "./utils"

export const getAllPhasesOfProject: (id: string) => Promise<[Phase[], any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `phases/project/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const getAllPhasesOfProjectByTask: (id: string) => Promise<[Phase[], any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `phases/task/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const getAllTaskOfPhase: (id: string) => Promise<[Task[], any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `phases/tasks/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const getPhase: (id: string) => Promise<[Phase, any]> = async (id) => {
    const endpointName = `phases/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Phase, null]
}

export const createPhase: (id: string, phase: Phase) => Promise<[Phase, any]> = async (id, phase) => {
    const [res, error] = await apiRequest({
        endpointName: `phases/${id}`,
        method: "POST",
        body: phase
    })

    if (error) {
        return [null, error]
    }

    return [res as Phase, null]
}

export const updatePhase: (phase: Phase) => Promise<[Phase, any]> = async (phase) => {
    const endpointName = `phases/${phase.id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: phase
    })

    if (error) {
        return [null, error]
    }

    return [res as Phase, null]
}

export const deletePhase: (id: string) => Promise<[Phase | null, any]> = async (id) => {
    const endpointName = `phases/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}