import type {Row, Schedule, ScheduleTask} from "../types/core.type"
import {apiRequest} from "./utils"

export const getScheduleByProject: (id: string) => Promise<[Schedule, any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `schedules/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Schedule, null]
}

export const getRows: (id: string) => Promise<[Row[], any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `schedules/rows/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Row[], null]
}

export const getTask: (id: string) => Promise<[ScheduleTask, any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `schedules/task/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as ScheduleTask, null]
}

export const createTask: (id: string, task: ScheduleTask) => Promise<[Schedule, any]> = async (id, task) => {
    const [res, error] = await apiRequest({
        endpointName: `schedules/task/${id}`,
        method: "POST",
        body: task
    })

    if (error) {
        return [null, error]
    }

    return [res as Schedule, null]
}
export const createRow: (id: string, row: Row) => Promise<[Schedule, any]> = async (id, row) => {
    const [res, error] = await apiRequest({
        endpointName: `schedules/row/${id}`,
        method: "POST",
        body: row
    })

    if (error) {
        return [null, error]
    }

    return [res as Schedule, null]
}

export const updateTask: (id: string, task: ScheduleTask) => Promise<[ScheduleTask, any]> = async (id, task) => {
    const [res, error] = await apiRequest({
        endpointName: `schedules/task/${id}`,
        method: "PUT",
        body: task
    })

    if (error) {
        return [null, error]
    }

    return [res as ScheduleTask, null]
}

export const updateRow: (id: string, row: Row) => Promise<[Row, any]> = async (id, row) => {
    const [res, error] = await apiRequest({
        endpointName: `schedules/row/${id}`,
        method: "PUT",
        body: row
    })

    if (error) {
        return [null, error]
    }

    return [res as Row, null]
}


export const deleteTask: (id: string) => Promise<[Schedule | null, any]> = async (id) => {
    const endpointName = `schedules/task/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const deleteRow: (id: string) => Promise<[Schedule | null, any]> = async (id) => {
    const endpointName = `schedules/row/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}