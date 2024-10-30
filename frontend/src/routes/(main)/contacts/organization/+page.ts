import { getAllOrganizations } from '$lib/organization.service';
import { getDataFromResponse } from '$lib/utils';

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
	const [successResponse, _] = await getAllOrganizations();
	return {
		organizations: getDataFromResponse(successResponse)
	};
}
