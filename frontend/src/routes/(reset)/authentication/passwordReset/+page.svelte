<script lang="ts">
	import { preventDefault } from 'svelte/legacy';

	import { apiRequest } from '$lib/utils';
	import { error, success } from '$lib/notification';
	import { goto } from '$app/navigation';
	import EmailInput from '$lib/components/core/EmailInput.svelte';
	import SubmitButton from '$lib/components/core/SubmitButton.svelte';

	let email = $state('');

	const handleSubmit = async () => {
		const [_, errorMessage] = await apiRequest({
			endpointName: 'users/passwordReset',
			method: 'POST',
			body: { email }
		});
		if (errorMessage) {
			error(errorMessage);
		} else {
			success(_);
			goto('/');
		}
	};
</script>

<div>
	<h2 class="text-3xl font-bold text-center text-gray-700">Forgotten Password?</h2>

	<p class="mt-1 text-center text-gray-500">Please enter your login email address.</p>

	<form onsubmit={preventDefault(handleSubmit)}>
		<div class="w-full mt-4">
			<EmailInput placeholder="Email Address" bind:value={email} />
		</div>

		<div class="flex items-center justify-between mt-4 mb-2">
			<span class="text-sm text-gray-600 hover:text-gray-500"></span>
			<SubmitButton text="Continue" />
		</div>
	</form>

	<div class="flex items-center justify-center py-4 text-center bg-gray-50">
		<span class="text-sm text-gray-600">Don't have an account? </span>

		<a href={'./register'} class="mx-2 text-sm font-bold text-blue-500 hover:underline">Register</a>
	</div>
</div>
