import type { Organization } from '$lib/types/core.type';
import { apiRequest } from './utils';

export const getAllOrganizations: () => Promise<[Organization[], any]> = async () => {
	const [res, error] = await apiRequest({
		endpointName: 'organizations',
		method: 'GET'
	});

	if (error) {
		return [null, error];
	}

	return [res, null];
};

export const getOrganization: (id: string) => Promise<[Organization, any]> = async (id) => {
	const endpointName = `organizations/${id}`;
	const [res, error] = await apiRequest({
		endpointName,
		method: 'GET'
	});

	if (error) {
		return [null, error];
	}

	return [res as Organization, null];
};

export const createOrganization: (
	organization: Organization
) => Promise<[Organization, any]> = async (project) => {
	const [res, error] = await apiRequest({
		endpointName: 'organizations',
		method: 'POST',
		body: project
	});

	if (error) {
		return [null, error];
	}

	return [res as Organization, null];
};

export const updateOrganization: (
	organization: Organization
) => Promise<[Organization, any]> = async (organization) => {
	const endpointName = `organizations/${organization.id}`;
	const [res, error] = await apiRequest({
		endpointName,
		method: 'PUT',
		body: organization
	});

	if (error) {
		return [null, error];
	}

	return [res as Organization, null];
};

export const deleteOrganization: (id: string) => Promise<[Organization | null, any]> = async (
	id
) => {
	const endpointName = `organizations/${id}`;
	const [res, error] = await apiRequest({
		endpointName,
		method: 'DELETE'
	});

	if (error) {
		return [null, error];
	}

	return [res, null];
};
