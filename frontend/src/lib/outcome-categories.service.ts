import type {OutcomeCategory} from "../types/core.type"
import {apiRequest} from "./utils"

export const getOutcomeCategoriesOfProject: (id: string) => Promise<[OutcomeCategory[], any]> = async (id) => {
    const [res, error] = await apiRequest({
        endpointName: `outcome-categories/project/${id}`,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as OutcomeCategory[], null]
}

export const getOutcomeCategory: (id: string) => Promise<[OutcomeCategory, any]> = async (id) => {
    const endpointName = `outcome-categories/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as OutcomeCategory, null]
}

export const createOutcomeCategory: (outcome: OutcomeCategory) => Promise<[OutcomeCategory, any]> = async (outcome) => {
    const [res, error] = await apiRequest({
        endpointName: `outcome-categories`,
        method: "POST",
        body: outcome
    })

    if (error) {
        return [null, error]
    }

    return [res as OutcomeCategory, null]
}

export const updateOutcomeCategory: (outcome: OutcomeCategory) => Promise<[OutcomeCategory, any]> = async (outcome) => {
    const endpointName = `outcome-categories/${outcome.id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: outcome
    })

    if (error) {
        return [null, error]
    }

    return [res as OutcomeCategory, null]
}

export const deleteOutcomeCategory: (id: string) => Promise<[OutcomeCategory | null, any]> = async (id) => {
    const endpointName = `outcome-categories/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}