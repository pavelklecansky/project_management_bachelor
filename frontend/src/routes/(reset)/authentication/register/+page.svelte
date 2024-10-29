<script lang="ts">
	import { signup } from '$lib/auth';
	import { error, success } from '$lib/notification';
	import { goto } from '$app/navigation';
	import { createForm } from 'svelte-forms-lib';
	import PasswordInput from '$lib/components/core/PasswordInput.svelte';
	import EmailInput from '$lib/components/core/EmailInput.svelte';
	import TextInput from '$lib/components/core/TextInput.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';

	const { form, errors, handleSubmit } = createForm({
		initialValues: {
			firstName: '',
			lastName: '',
			email: '',
			password: '',
			passcode: '',
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
			const [signUpSuccess, signUpError] = await signup(
				values.email,
				values.password,
				values.firstName,
				values.lastName,
				values.passcode
			);
			if (signUpError) {
				error(signUpError);
			} else {
				success(signUpSuccess);
				goto('/');
			}
		}
	});
</script>

<div>
	<h2 class="text-3xl font-bold text-center text-gray-700">Welcome</h2>

	<p class="mt-1 text-center text-gray-500">Create new account</p>

	<form on:submit|preventDefault={handleSubmit}>
		<div class="w-full mt-4">
			<TextInput placeholder="Firstname" bind:value={$form.firstName} />
		</div>

		<div class="w-full mt-4">
			<TextInput placeholder="Lastname" bind:value={$form.lastName} />
		</div>

		<div class="w-full mt-4">
			<EmailInput placeholder="Email Address" bind:value={$form.email} />
			{#if $errors.email}
				{error($errors.email)}
			{/if}
		</div>

		<div class="w-full mt-4">
			<PasswordInput placeholder="Password" bind:value={$form.password} />
		</div>

		<div class="w-full mt-4">
			<PasswordInput placeholder="Confirm password" bind:value={$form.confirmPassword} />
			{#if $errors.confirmPassword}
				{error($errors.confirmPassword) || ''}
			{/if}
		</div>

		<div class="w-full mt-4">
			<TextInput placeholder="Passcode" bind:value={$form.passcode} />
		</div>

		<div class="flex items-center justify-between mt-4 mb-2">
			<span class="text-sm text-gray-600 hover:text-gray-500" />

			<SubmitButton text="Register" />
		</div>
	</form>

	<div class="flex items-center justify-center py-4 text-center bg-gray-50">
		<span class="text-sm text-gray-600">Already have an account? </span>

		<a href={'./'} class="mx-2 text-sm font-bold text-blue-500 hover:underline">Login</a>
	</div>
</div>
