import { getDataFromResponse } from '$lib/utils';
import { getGroups } from '$lib/groups.service';

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
	const [successResponse, _] = await getGroups();
	return {
		groups: getDataFromResponse(successResponse)
	};
}
