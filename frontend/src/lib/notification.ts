import {toast} from "@zerodevx/svelte-toast";

export const error: (message: any) => void = (message: any) => {
    let errorMessage = message.hasOwnProperty("message")
                ? message.message
                : message;
    toast.push(errorMessage, {
        theme: {
            "--toastBackground": "#F56565",
            "--toastBarBackground": "#C53030",
        },
    });
}

export const success: (message: any) => void = (message: any) => {
    let successMessage = message.hasOwnProperty("message")
                ? message.message
                : message;
    toast.push(successMessage, {
        theme: {
            '--toastBackground': '#48BB78',
            '--toastBarBackground': '#2F855A'
        }
    });
}