import type {Outcome} from "../types/core.type"
import {apiRequest} from "./utils"

export const getOutcomesOfProject: (id: string) => Promise<[Outcome[], any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `outcomes/project/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Outcome[], null]
}

export const getOutcomesOfCategory: (id: string) => Promise<[Outcome[], any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `outcomes/category/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Outcome[], null]
}

export const getOutcome: (id: string) => Promise<[Outcome, any]> = async (id) => {
    const endpointName = `outcomes/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Outcome, null]
}

export const createOutcome: (outcome: Outcome) => Promise<[Outcome, any]> = async (outcome) => {
    const [res, error] = await apiRequest({
        endpointName: `outcomes`,
        method: "POST",
        body: outcome
    })

    if (error) {
        return [null, error]
    }

    return [res as Outcome, null]
}

export const updateOutcome: (outcome: Outcome) => Promise<[Outcome, any]> = async (outcome) => {
    const endpointName = `outcomes/${outcome.id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: outcome
    })

    if (error) {
        return [null, error]
    }

    return [res as Outcome, null]
}

export const deleteOutcome: (id: string) => Promise<[Outcome | null, any]> = async (id) => {
    const endpointName = `outcomes/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}