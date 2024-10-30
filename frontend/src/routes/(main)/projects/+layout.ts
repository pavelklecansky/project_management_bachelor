import { getAllProjects } from '$lib/project.service';

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
	const [success] = await getAllProjects();
	return {
		projects: success
	};
}
