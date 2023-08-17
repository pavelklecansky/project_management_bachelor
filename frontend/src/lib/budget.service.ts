import type { Budget, BudgetCategory, BudgetItem } from '$lib/types/core.type';
import { apiRequest } from './utils';

export const getBudgetByProject: (id: string) => Promise<[Budget, any]> = async (id) => {
	const [res, error] = await apiRequest({
		endpointName: `budget/${id}`,
		method: 'GET'
	});

	if (error) {
		return [null, error];
	}

	return [res as Budget, null];
};

export const getCategory: (id: string) => Promise<[BudgetCategory, any]> = async (id) => {
	const endpointName = `budget/category/${id}`;
	const [res, error] = await apiRequest({
		endpointName,
		method: 'GET'
	});

	if (error) {
		return [null, error];
	}

	return [res as BudgetCategory, null];
};

export const getItemCategory: (id: string) => Promise<[BudgetCategory, any]> = async (id) => {
	const endpointName = `budget/itemCategory/${id}`;
	const [res, error] = await apiRequest({
		endpointName,
		method: 'GET'
	});

	if (error) {
		return [null, error];
	}

	return [res as BudgetCategory, null];
};

export const getItem: (id: string) => Promise<[BudgetItem, any]> = async (id) => {
	const endpointName = `budget/item/${id}`;
	const [res, error] = await apiRequest({
		endpointName,
		method: 'GET'
	});

	if (error) {
		return [null, error];
	}

	return [res as BudgetItem, null];
};

export const createBudgetCategory: (
	id: string,
	budgetCategory: BudgetCategory
) => Promise<[BudgetCategory, any]> = async (id, budgetCategory) => {
	const [res, error] = await apiRequest({
		endpointName: `budget/category/${id}`,
		method: 'POST',
		body: budgetCategory
	});

	if (error) {
		return [null, error];
	}

	return [res as BudgetCategory, null];
};
export const createBudgetItem: (
	id: string,
	budgetItem: BudgetItem
) => Promise<[BudgetItem, any]> = async (id, budgetItem) => {
	const [res, error] = await apiRequest({
		endpointName: `budget/item/${id}`,
		method: 'POST',
		body: budgetItem
	});

	if (error) {
		return [null, error];
	}

	return [res as BudgetItem, null];
};

export const updateCategory: (
	id: string,
	category: BudgetCategory
) => Promise<[BudgetCategory, any]> = async (id, category) => {
	const [res, error] = await apiRequest({
		endpointName: `budget/category/${id}`,
		method: 'PUT',
		body: category
	});

	if (error) {
		return [null, error];
	}

	return [res as BudgetCategory, null];
};

export const updateItem: (id: string, item: BudgetItem) => Promise<[BudgetItem, any]> = async (
	id,
	item
) => {
	const [res, error] = await apiRequest({
		endpointName: `budget/item/${id}`,
		method: 'PUT',
		body: item
	});

	if (error) {
		return [null, error];
	}

	return [res as BudgetItem, null];
};

export const deleteBudgetItem: (id: string) => Promise<[BudgetItem | null, any]> = async (id) => {
	const endpointName = `budget/item/${id}`;
	const [res, error] = await apiRequest({
		endpointName,
		method: 'DELETE'
	});

	if (error) {
		return [null, error];
	}

	return [res, null];
};

export const deleteBudgetCategory: (id: string) => Promise<[BudgetCategory | null, any]> = async (
	id
) => {
	const endpointName = `budget/category/${id}`;
	const [res, error] = await apiRequest({
		endpointName,
		method: 'DELETE'
	});

	if (error) {
		return [null, error];
	}

	return [res, null];
};
