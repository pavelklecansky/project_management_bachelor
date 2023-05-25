import {apiRequest, transferify} from "./utils"
import type {User} from '../types/authentication.type'


export const updateUser: (user: User) => any = async (user) => {
    const endpointName = `users/${user.id}`

    user.organizations = user.organizations.map(value => transferify(value, true));

    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: user
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const changePassword: (currentPassword: string, newPassword: string) => any = async (currentPassword, newPassword) => {
    const [res, error] = await apiRequest({
        endpointName: "users/changePassword",
        method: "POST",
        body: { currentPassword, newPassword }
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const getAllUsers: () => Promise<[User[] | null, any]> = async () => {
    const [res, error] = await apiRequest({
        endpointName: "users",
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as User[], null]
}

export const getUser: (id: string) => Promise<[User | null, any]> = async (id) => {
    const endpointName = `users/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "GET",
    })

    if (error) {
        return [null, error]
    }

    return [res as User, null]
}

export const deleteUser: (id: string) => Promise<[User | null, any]> = async (id) => {
    const endpointName = `users/${id}`;
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const generatePasscode: () => any = async () => {
    const [res, error] = await apiRequest({
        endpointName: "users/generatePasscode",
        method: "POST",
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}