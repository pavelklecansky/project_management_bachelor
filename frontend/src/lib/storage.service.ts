import {apiRequest} from "./utils"

export const listFiles: (id: string, path) => Promise<any[]> = async (id, path = '') => {
    const endpointName = `files/${id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "POST",
        body: {path}
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}


export const downloadFile: (id: string, name: string, path: string) => Promise<any> = async (id, name, path = "") => {
    const endpointName = `files/${id}/${name}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "POST",
        body: {path},
        responseType: "blob"
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const downloadFileInBrowser: (id: string, name: string, path: string) => Promise<void> = async (id, name, path = "") => {
    const [success, errorMessage] = await downloadFile(id, name, path);
    const url = URL.createObjectURL(success);
    console.log(url);
    const anchor = document.createElement("a");
    anchor.href = url;
    anchor.download = name;

    document.body.appendChild(anchor);
    anchor.click();
    document.body.removeChild(anchor);

    URL.revokeObjectURL(url);
}

export const getFileUrl: (id: string, name: string, path: string) => Promise<string> = async (id, name, path = "") => {
    const [success, errorMessage] = await downloadFile(id, name, path);
    const url = URL.createObjectURL(success);
    return url;
}

export const uploadFile: (id: string, formData: FormData, path: string) => Promise<any> = async (id, formData, path) => {
    const endpointName = `files/uploadFile/${id}`
    formData.set("path", path || "");
    const [res, error] = await apiRequest({
        endpointName,
        method: "POST",
        body: formData,
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}


export const createFolder: (id: string, name: string, path) => Promise<any> = async (id, name, path = '') => {
    const endpointName = `files/folder/${id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "POST",
        body: {name, path}
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const deleteFile: (id: string, name: string, path) => Promise<any> = async (id, name, path = '') => {
    const endpointName = `files/${id}/${name}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
        body: {path}
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const deleteFolder: (id: string, name: string, path) => Promise<any> = async (id, name, path = '') => {
    const endpointName = `files/folder/${id}/${name}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "DELETE",
        body: {path}
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}

export const renameFile: (id: string, oldName: string, newName: string, path) => Promise<any> = async (id, oldName, newName, path = '') => {
    const endpointName = `files/${id}`
    const [res, error] = await apiRequest({
        endpointName,
        method: "PUT",
        body: {
            oldName,
            newName,
            path
        }
    })

    if (error) {
        return [null, error]
    }

    return [res, null]
}
