import { getProject } from '$lib/project.service';
import { error } from '$lib/notification';
import { redirect } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
	const [success, errorMessage] = await getProject(params.id);
	if (!success || errorMessage) {
		error(errorMessage);
		redirect(307, `/`);
	}

	return {
		project: success!
	};
}
