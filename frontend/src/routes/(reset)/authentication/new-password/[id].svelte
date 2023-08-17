<script lang="ts">
	import { page } from '$app/stores';
	import { error, success } from '$lib/notification';
	import { onMount } from 'svelte';
	import { createForm } from 'svelte-forms-lib';
	import { apiRequest } from '$lib/utils';
	import PasswordInput from '$lib/components/core/PasswordInput.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';
	import { goto } from '$app/navigation';

	let ready = false;

	onMount(async () => {
		let id = $page.params.id;
		const [res, errorMessage] = await apiRequest({
			endpointName: 'users/passwordReset/check',
			method: 'POST',
			body: { id }
		});
		if (!res || errorMessage) {
			goto('../error');
		}
		ready = true;
	});

	const { form, errors, handleSubmit } = createForm({
		initialValues: {
			password: '',
			confirmPassword: ''
		},

		validate: (values) => {
			let errors = {};
			if (!values.confirmPassword) {
				errors['confirmPassword'] = 'Confirm password is required.';
			} else if (values.confirmPassword !== values.password) {
				errors['confirmPassword'] = 'Passwords does not match.';
			}
			return errors;
		},

		onSubmit: async (values) => {
			let id = $page.params.id;
			const [res, errorMessage] = await apiRequest({
				endpointName: 'users/newPassword',
				method: 'POST',
				body: {
					token: id,
					newPassword: values.password
				}
			});
			if (!res || errorMessage) {
				error(errorMessage);
			}
			success(res);
			goto('../');
		}
	});
</script>

{#if ready}
	<div>
		<h2 class="text-3xl font-bold text-center text-gray-700 dark:text-white">
			Reset Your Password
		</h2>

		<p class="mt-1 text-center text-gray-500 dark:text-gray-400">Please enter your new password.</p>

		<form on:submit|preventDefault={handleSubmit}>
			<div class="w-full mt-4">
				<PasswordInput placeholder="Password" bind:value={$form.password} />
			</div>

			<div class="w-full mt-4">
				<PasswordInput placeholder="Confirm password" bind:value={$form.confirmPassword} />
				{#if $errors.confirmPassword}
					{error($errors.confirmPassword) || ''}
				{/if}
			</div>

			<div class="flex items-center justify-between mt-4 mb-2">
				<span class="text-sm text-gray-600 dark:text-gray-200 hover:text-gray-500" />
				<SubmitButton text="Continue" />
			</div>
		</form>

		<div class="flex items-center justify-center py-4 text-center bg-gray-50 dark:bg-gray-700">
			<span class="text-sm text-gray-600 dark:text-gray-200">Don't have an account? </span>

			<a
				href={'./register'}
				class="mx-2 text-sm font-bold text-blue-500 dark:text-blue-400 hover:underline">Register</a
			>
		</div>
	</div>
{/if}
