import { writable } from 'svelte/store';
import { authState } from './auth';
import { getAllProjects } from './project.service';

export const projects = writable([]);

export const load = async () => {
	const [success] = await getAllProjects();
	projects.set(success);
};

authState.subscribe(async (value) => {
	if (value.isSignedIn) {
		await load();
	}
});
