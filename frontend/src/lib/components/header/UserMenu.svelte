<script lang="ts">
	import { authState, signout } from '$lib/auth';
	import { goto } from '$app/navigation';
	import { getAvatarUrl } from '$lib/avatar';

	let hidden = true;

	$: user = $authState.user;
	$: fullName = $authState.user.firstName + ' ' + $authState.user.lastName;

	const userSignout = async () => {
		const [_, error] = await signout();
		if (!error) {
			goto('/authentication');
		}
	};
</script>

<li class="inline-block relative text-left">
	<span class="flex items-center mx-2 cursor-pointer" on:click={() => (hidden = !hidden)}>
		<img
			class="w-8 h-8 mr-3 rounded-full object-cover"
			src={getAvatarUrl(user.firstName, user.lastName)}
			alt={fullName}
		/>
		<div class="mt-1 md:block">
			<div class="leading-3 text-sm text-gray-700">{fullName}</div>
			<small class="text-xs text-gray-500">{user?.organizations?.find(Boolean)?.name || ''}</small>
		</div>
	</span>

	<div class="origin-top-right absolute right-0 mt-3 z-56 w-56 rounded-md shadow-lg" class:hidden>
		<div class="rounded-md bg-white shadow-xs">
			<div class="py-1">
				<a
					href={'/user'}
					class="block px-4 py-2 text-sm leading-5 text-gray-700 hover:bg-gray-100 hover:text-gray-900 focus:outline-none focus:bg-gray-100 focus:text-gray-900"
					>User account</a
				>
				<span
					on:click={userSignout}
					class="block cursor-pointer px-4 py-2 text-sm leading-5 text-gray-700 hover:bg-gray-100 hover:text-gray-900 focus:outline-none focus:bg-gray-100 focus:text-gray-900"
					>Logout</span
				>
			</div>
		</div>
	</div>
</li>
