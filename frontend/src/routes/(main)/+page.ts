import { getAllOrganizations } from '$lib/organization.service';
import { getDataFromResponse } from '$lib/utils';
import { getAllProjects } from '$lib/project.service';
import { getAllUserTasks } from '$lib/task.service';

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
	const [projects] = await getAllProjects();
	const [userTasks, _] = await getAllUserTasks();
	return {
		projects: projects,
		userTasks: userTasks
	};
}
