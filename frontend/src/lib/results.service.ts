import type {Result} from "$lib/types/core.type"
import {apiRequest} from "./utils"

export const getResult: (id: string) => Promise<[Result, any]> = async (id) => {
    const endpointName = `results/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Result, null]
}

export const createResult: (result: Result) => Promise<[Result, any]> = async (result) => {
    const [res, error] = await apiRequest({
        endpointName: `results`,
        method: "POST",
        body: result
    })

    if (error) {
        return [null, error]
    }

    return [res as Result, null]
}

export const updateResult: (result: Result) => Promise<[Result, any]> = async (result) => {
    const endpointName = `results/${result.id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: result
    })

    if (error) {
        return [null, error]
    }

    return [res as Result, null]
}

export const deleteResult: (id: string) => Promise<[Result | null, any]> = async (id) => {
    const endpointName = `results/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}