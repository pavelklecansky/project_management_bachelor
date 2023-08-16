import {endpoint} from "./consts"
import {get as getStoreValue} from "svelte/store"
import {authState, signout} from "./auth"
import type {BudgetItem, ErrorMessage, Priority, ScheduleTask, Status} from "$lib/types/core.type";
import axios, {type ResponseType} from 'axios';
import moment from 'moment';
import type {Page} from '@sveltejs/kit';

export interface RequestOptions {
    endpointName: string,
    body?: {},
    method?: "GET" | "POST" | "PUT" | "DELETE",
    responseType?: ResponseType
}

export function isActive(page: Page<Record<string, string>>, path: string) {
    const pathname = page.url.pathname;
    return pathname === path;
}

export type ApiResponse = Promise<[any, null] | [null, ErrorMessage | string]>

export const getAuthorizationHeader: () => string | null = () => {
    const {accessToken, isSignedIn} = getStoreValue(authState)
    if (isSignedIn) {
        return `Bearer ${accessToken}`
    }
    return null
}

export async function apiRequest(options: RequestOptions): ApiResponse {
    const {
        endpointName,
        body = null,
        method = "GET",
        responseType = "json"
    } = options
    try {
        const res = await axios(
            endpoint(endpointName),
            {
                method,
                data: method !== "GET" ? body : null,
                responseType,
                headers: {
                    ...(getAuthorizationHeader() ? {"Authorization": getAuthorizationHeader()} : {}),
                }
            }
        )
        let data = await res.data;
        return [data, null];
    } catch (error) {
        console.log(error);
        ifExpiredOrInvalidJWTTokenSignout(error.response);
        return [null, error.response.data];
    }
}

export function getItemsSum(items: BudgetItem[]) {
    return items.map(value => value.budget).reduce((partialSum, a) => partialSum + a, 0);
}

export const getDataFromResponse: (response: any) => any = (response: any) => {
    return response.hasOwnProperty("data")
        ? response.data
        : response;
}

export const dateISOFormat: (date: Date) => string = (date) => {
    return new Date(date)
        .toISOString()
        .slice(0, 10)
}

export const dateISOFormatFull: (date: Date) => string = (date) => {
    let newDate = new Date(date);
    newDate.setMinutes(newDate.getMinutes() - newDate.getTimezoneOffset());
    return newDate.toISOString().slice(0, 16)
}

export function time(input) {
    return moment(input, 'HH:mm');
}

export const priorityLabelFromValue: (value: string) => string = (value) => {
    switch (value) {
        case "LOW":
            return "🟢 Low";
        case "NORMAL":
            return "🔵 Normal";
        case "HIGH":
            return "🟠 High";
        case "IMMEDIATE":
            return "🔴 Immediate";
        default:
            return "🤷‍♂️ Unknown"
    }
}

export const getAllPriorityTypes: () => Priority[] = () => {
    return [
        {value: "LOW", label: priorityLabelFromValue("LOW")},
        {value: "NORMAL", label: priorityLabelFromValue("NORMAL")},
        {value: "HIGH", label: priorityLabelFromValue("HIGH")},
        {value: "IMMEDIATE", label: priorityLabelFromValue("IMMEDIATE")},
    ];
}

export const getDefaultPriorityType: () => Priority = () => {
    return {value: "NORMAL", label: priorityLabelFromValue("NORMAL")}
}

export const getAllStatusTypes: () => Status[] = () => {
    return [
        {value: "NEW", label: statusLabelFromValue("NEW")},
        {value: "IN_PROGRESS", label: statusLabelFromValue("IN_PROGRESS")},
        {value: "COMPLETED", label: statusLabelFromValue("COMPLETED")},
        {value: "ON_HOLD", label: statusLabelFromValue("ON_HOLD")},
        {value: "REJECTED", label: statusLabelFromValue("REJECTED")},
    ];
}

export const getDefaultStatusType: () => Status = () => {
    return {value: "NEW", label: statusLabelFromValue("NEW")}
}

export const statusLabelFromValue: (value: string) => string = (value) => {
    switch (value) {
        case "NEW":
            return "👶 New";
        case "IN_PROGRESS":
            return "🛠 In progress";
        case "COMPLETED":
            return "✔ Completed";
        case "ON_HOLD":
            return "⌛ On hold";
        case "REJECTED":
            return "❌ Rejected";
        default:
            return "🤷‍♂️ Unknown"
    }
}

export const transferify: (object: any, topLevel?: boolean) => any = (object, topLevel = false) => {
    if (!object) {
        return null;
    }

    if (topLevel) {
        if (object.hasOwnProperty("id")) {
            return object.id;
        }
    }

    for (const key in object) {
        if (object[key]) {
            if (object[key].hasOwnProperty("id")) {
                const id = object[key].id;
                object[key] = id;
            }
        }
    }

    return object;
}

export const isEmptyObject: (obj: any) => boolean = (obj) => {
    return obj && Object.keys(obj).length === 0 && Object.getPrototypeOf(obj) === Object.prototype;
}

export const isEmptyObjectOrNull: (obj: any) => boolean = (obj) => {
    return obj === null || isEmptyObject(obj);
}

export const backendScheduleTaskToFrontend: (tasks: ScheduleTask[]) => any[] = (tasks) => {
    return tasks.map(({fromDate, toDate, ...rest}) => ({
        ...rest,
        from: moment(fromDate),
        to: moment(toDate),
        enableDragging: false,
        popUpHidden: true
    }));
}


export function createPopup(task, node, projectId) {
    if (!task.popUpHidden) {
        document.getElementById("sg-group").outerHTML = "";
        task.popUpHidden = true;
    } else {
        const content = node.getElementsByClassName("sg-task-content")[0];
        content.style.position = 'relative';
        const rect = content.getBoundingClientRect();
        const div = document.createElement('div');
        div.className = 'sg-popup';
        div.id = 'sg-group';
        div.innerHTML = `
            <div class="sg-popup-item">
                <a href="/project/schedule/${projectId}/delete-task/${task.realId}">
                <span
                  class="block cursor-pointer px-4 py-2 text-sm leading-5 text-red-600 font-bold hover:bg-gray-100 hover:text-red-700"
                  >DELETE TASK</span></a>
            </div>
            <div class="sg-popup-item">
                <a href="/project/schedule/${projectId}/edit-task/${task.realId}">
                <span
                 class="block cursor-pointer px-4 py-2 text-sm leading-5 text-green-600 font-bold hover:bg-gray-100 hover:text-green-700"
                 >EDIT TASK</span>
                 </a>
            </div>`;
        div.style.position = 'absolute';
        console.log(rect.left)
        console.log(rect.width)
        console.log(rect.left + rect.width / 2)
        console.log(content)
        div.style.top = `0px`;
        div.style.left = `0px`;
        content.appendChild(div);
        task.popUpHidden = false;
        return div;
    }
}


function ifExpiredOrInvalidJWTTokenSignout(response) {
    if (response.data.message === "Expired or invalid JWT token" || response.status === 401) {
        signout();
    }
}
