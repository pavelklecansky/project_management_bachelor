import { writable } from 'svelte/store';
import { getAllUsers } from '$lib/user';
import type { User } from '$lib/types/authentication.type';
import { authState } from './auth';

export const users = writable<User[]>();

export const load = async () => {
	const [success] = await getAllUsers();
	users.set(success!);
};

authState.subscribe(async (value) => {
	if (value.isSignedIn) {
		await load();
	}
});
