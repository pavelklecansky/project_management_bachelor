import {AUTH_STATE_KEY} from "./consts"
import {get as getStoreValue} from 'svelte/store'
import {writable} from 'svelte-local-storage-store'
import {apiRequest, getDataFromResponse} from "./utils"
import type {User} from "$lib/types/authentication.type";
import {Role} from "$lib/types/role.enum";
import {updateUser} from './user';

export const authState = writable(AUTH_STATE_KEY, {
    accessToken: "",
    refreshToken: "",
    isSignedIn: false,
    user: {} as User
})


function setTokens({refresh_token = null, accessToken = null, user = null}) {
    const access_token = accessToken // API of function is like this to allow us to give response directly to function
    authState.update(currentState => {
        const newState = {...currentState};

        if (access_token) {
            newState.accessToken = access_token;
            newState.isSignedIn = true;
        } else {
            newState.accessToken = "";
            newState.isSignedIn = false;
        }

        if (refresh_token) {
            newState.refreshToken = refresh_token;
        } else {
            newState.refreshToken = "";
        }

        if (user) {
            newState.user = user;
        } else {
            newState.user = {} as User;
        }

        return newState;
    })
}

export const isAdmin: () => boolean = () => {
    const roles = getUserRoles();
    return roles.includes(Role.ROLE_ADMIN) || roles.includes(Role.ROLE_SUPER_ADMIN);
}

export const isSuperAdmin: () => boolean = () => {
    const roles = getUserRoles();
    return roles.includes(Role.ROLE_SUPER_ADMIN);
}

export const getUserRoles: () => Role[] = () => {
    return getStoreValue(authState).user.roles;
}

export const isSignedIn: () => Promise<boolean> = async () => {
    return getStoreValue(authState).isSignedIn;
}

export const signIn: (email: string, password: string) => any = async (email, password) => {
    if (getStoreValue(authState).isSignedIn) {
        return [null, "already logged in"]
    }

    const [res, error] = await apiRequest({
        endpointName: "users/login",
        method: "POST",
        body: {email, password}
    })


    if (error) {
        return [null, error]
    }

    setTokens(res)
    return [getStoreValue(authState).isSignedIn, null]
}

export const signout: () => any = async () => {
    const currentAuthState = getStoreValue(authState)
    if (!currentAuthState.isSignedIn) {
        return [null, "not signed in"]
    }

    authState.set({
        accessToken: "",
        refreshToken: "",
        isSignedIn: false,
        user: {} as User
    })

    return ["is signout", null]
}

export const signup: (email: string, password: string, firstName: string, lastName: string, passcode: string) => any = async (email, password, firstName, lastName, passcode) => {
    const currentAuthState = getStoreValue(authState)
    if (currentAuthState.isSignedIn) {
        return [null, "already signed in"]
    }

    const [res, error] = await apiRequest({
        endpointName: "users/register",
        method: "POST",
        body: {email, password, firstName, lastName, passcode},
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const updateLoginUser: (user: User) => any = async (user) => {

    const [res, error] = await updateUser(user);

    if (error) {
        return [null, error]
    }

    authState.update(contents => {
        contents.user = getDataFromResponse(res);
        return contents;
    });

    return [res, null]
}