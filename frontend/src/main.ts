import HMR from '@roxi/routify/hmr'
import App from './App.svelte'
import axios from 'axios';
import {getAuthorizationHeader} from './lib/utils';

const app = HMR(App, {target: document.body}, 'routify-app')


axios.interceptors.request.use(function (config) {
    config.headers = {
        ...(getAuthorizationHeader() ? {"Authorization": getAuthorizationHeader()} : {}),
        ...config.headers
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

export default app
