import {writable} from 'svelte/store';
import {getAllUsers} from "../lib/user";
import type {User} from '../types/authentication.type';
import {authState} from './auth';

export const users = writable<User[]>();

export const load = async () => {
    const [success] = await getAllUsers();
    users.set(success!);
};

authState.subscribe(value => {
    if (value.isSignedIn) {
        load();
    }
});

