import type {User} from "../types/authentication.type"
import type {Group, Project} from "../types/core.type"
import {apiRequest, transferify} from "./utils"

export const getAllProjects: () => Promise<any[]> = async () => {
    const [res, error] = await apiRequest({
        endpointName: "projects",
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const getProject: (id: string) => Promise<[Project | null, any]> = async (id) => {
    const endpointName = `projects/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as Project, null]
}

export const createProject: (project: Project) => Promise<[Project | null, any]> = async (project) => {
    const [res, error] = await apiRequest({
        endpointName: "projects",
        method: "POST",
        body: project
    })

    if (error) {
        return [null, error]
    }

    return [res as Project, null]
}

export const addMember: (id: string, user: User) => Promise<[Project | null, any]> = async (id, user) => {
    const endpointName = `projects/${id}/member`
    const [res, error] = await apiRequest({
        endpointName,
        method: "POST",
        body: {
            user: transferify(user, true)
        }
    })

    if (error) {
        return [null, error]
    }

    return [res as Project, null]
}

export const deleteMember: (id: string, idMember: string) => Promise<[Project | null, any]> = async (id, idMember) => {
    const endpointName = `projects/${id}/member/${idMember}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res as Project, null]
}

export const addGroupMember: (id: string, group: Group) => Promise<[Project | null, any]> = async (id, group) => {
    const endpointName = `projects/${id}/group-member`
    const [res, error] = await apiRequest({
        endpointName,
        method: "POST",
        body: {
            group: transferify(group, true)
        }
    })

    if (error) {
        return [null, error]
    }

    return [res as Project, null]
}

export const deleteGroupMember: (id: string, idMember: string) => Promise<[Project | null, any]> = async (id, idMember) => {
    const endpointName = `projects/${id}/group-member/${idMember}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res as Project, null]
}


export const addComment: (id: string, text: string) => Promise<[Project | null, any]> = async (id, text) => {
    const endpointName = `projects/${id}/comment`
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

    return [res as Project, null]
}

export const deleteComment: (id: string, idComment: string) => Promise<[Project | null, any]> = async (id, idComment) => {
    const endpointName = `projects/${id}/comment/${idComment}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res as Project, null]
}

export const updateProject: (project: Project) => Promise<[Project | null, any]> = async (project) => {
    const endpointName = `projects/${project.id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: project
    })

    if (error) {
        return [null, error]
    }

    return [res as Project, null]
}

export const deleteProject: (id: string) => Promise<[Project | null, any]> = async (id) => {
    const endpointName = `projects/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}