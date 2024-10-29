<script lang="ts">
	import '../../global.css';
	import { goto } from '$app/navigation';
	import { isSignedIn } from '$lib/auth';
</script>

{#await isSignedIn()}
	Loading
{:then authorized}
	{#if authorized}
		{goto('/') || ''}
	{:else}
		<div class="flex items-center justify-center h-screen">
			<div
				class="w-full border-gray-100 max-w-sm mx-auto overflow-hidden bg-white rounded-lg shadow-xl align-middle"
			>
				<div class="px-6 py-4">
					<slot />
				</div>
			</div>
		</div>
	{/if}
{:catch}
	{goto('/') || ''}
{/await}
