import type {Group} from "$lib/types/core.type"
import {apiRequest, transferify} from "./utils"

export const getGroups: () => Promise<[Group[], any]> = async () => {
    const [res, error] = await apiRequest({
        endpointName: "groups",
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const getGroup: (id: string) => Promise<[Group, any]> = async (id) => {
    const endpointName = `groups/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Group, null]
}

export const deleteGroup: (id: string) => Promise<[any, any]> = async (id) => {
    const endpointName = `groups/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const updateGroup: (group: Group) => Promise<[Group, any]> = async (group) => {
    group.members = group.members.map(member => {
        return transferify(member);
    });
    const endpointName = `groups/${group.id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: group
    })

    if (error) {
        return [null, error]
    }

    return [res as Group, null]
}

export const createGroup: (group: Group) => Promise<[Group, any]> = async (group) => {

    group.members = group.members.map(member => {
        return transferify(member);
    });

    const [res, error] = await apiRequest({
        endpointName: "groups",
        method: "POST",
        body: transferify(group)
    })

    if (error) {
        return [null, error]
    }

    return [res as Group, null]
}