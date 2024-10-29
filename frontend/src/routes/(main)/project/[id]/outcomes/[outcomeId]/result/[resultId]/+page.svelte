<script lang="ts">
	import { run, stopPropagation } from 'svelte/legacy';

	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { getContext, onMount } from 'svelte';
	import EditButton from '$lib/components/core/EditButton.svelte';
	import DeleteButton from '$lib/components/core/DeleteButton.svelte';
	import { error, success } from '$lib/notification';
	import type { Result } from '$lib/types/core.type';
	import { getResult } from '$lib/results.service';
	import UploadButton from '$lib/components/core/UploadButton.svelte';
	import { deleteFile, downloadFileInBrowser, listFiles, uploadFile } from '$lib/storage.service';
	import OveflowMenu from '$lib/components/core/OveflowMenu.svelte';
	import Document from 'carbon-icons-svelte/lib/Document.svelte';
	import EditFilePopup from '$lib/components/storage/EditFilePopup.svelte';

	let id = $page.params.resultId;
	let result = $state({} as Result);
	let filesInfo = $state([]);
	let files = $state([]);
	let loaded = $state(false);

	const { open } = getContext('simple-modal');

	onMount(async () => {
		const [success, errorMessage] = await getResult(id);
		if (!success || errorMessage) {
			error(errorMessage);
			await goto(`./../`);
		} else {
			result = success!;
			await loadFilesInfo();
			loaded = true;
		}
	});

	const loadFilesInfo = async () => {
		const [success, errorMessage] = await listFiles(id);
		if (!success || errorMessage) {
			goto(`./../`);
		}
		filesInfo = success!;
		filesInfo.forEach(function (element) {
			element.hidden = true;
		});
	};

	const download = async (name) => {
		await downloadFileInBrowser(id, name);
	};

	const deleteF = async (name) => {
		const [successMessage, errorMessage] = await deleteFile(id, name);
		if (!successMessage || errorMessage) {
			error(errorMessage);
		} else {
			success(successMessage);
		}
		await loadFilesInfo();
	};

	const renameFileModal = async (oldName: string) => {
		await open(
			EditFilePopup,
			{ oldName, id },
			{},
			{
				onClosed: async () => {
					await loadFilesInfo();
				}
			}
		);
	};

	run(() => {
		if (files.length === 1) {
			const formData = new FormData();
			formData.append('file', files[0]);
			uploadFile(id, formData)
				.then((response) => {
					const [successMessage, errorMessage] = response;
					if (!successMessage || errorMessage) {
						error(errorMessage);
					} else {
						success(successMessage);
					}
				})
				.finally(async () => {
					files = [];
					await loadFilesInfo();
				});
		}
	});
</script>

<div>
	{#if loaded}
		<div>
			<h1 class="text-4xl font-bold">{result.name}</h1>
			<div class="flex justify-between gap-4">
				<div class="w-4/5">
					<div class="mt-2">
						<h2 class="text-xl">Description</h2>
						<p>
							{result.description}
						</p>
					</div>
				</div>
				<div class="w-1/5 flex flex-col text-center">
					<h3 class="text-1xl font-bold">Actions</h3>
					<a class="mt-2" href={`./delete/${result.id}`}>
						<DeleteButton text={'Delete result'} full={true} />
					</a>
					<a class="mt-2" href={`./edit/${result.id}`}>
						<EditButton text={'Edit result'} full={true} />
					</a>
				</div>
			</div>
		</div>
		<h1 class="text-2xl">Files</h1>
		<div class="flex justify-end w-full mb-2">
			<UploadButton text={'Add new file'} bind:files />
		</div>
		<div class="flex flex-wrap m-4 gap-5">
			{#each filesInfo as file}
				<div
					class="w-1/2 sm:w-1/3 lg:w-1/5 relative rounded-md pt-8 pb-5 text-center max-w-sm px-4 py-3 bg-white shadow-md box-border flex flex-col items-center cursor-pointer"
					onclick={() => download(file.name)}
					title={file.name}
				>
					<div onclick={stopPropagation(() => {})}>
						<OveflowMenu bind:hidden={file.hidden}>
							<span
								class="block cursor-pointer px-4 py-2 text-sm leading-5 text-red-600 font-bold hover:bg-gray-100 hover:text-red-700"
								onclick={() => {
									deleteF(file.name);
									file.hidden = true;
								}}>DELETE FILE</span
							>
							<span
								class="block cursor-pointer px-4 py-2 text-sm leading-5 text-green-600 font-bold hover:bg-gray-100 hover:text-green-700"
								onclick={() => {
									renameFileModal(file.name);
									file.hidden = true;
								}}>EDIT FILE NAME</span
							>
						</OveflowMenu>
					</div>

					<div>
						<Document class="w-32 h-32 fill-current" />
					</div>

					<div class="w-5/6">
						<p class="truncate">{file.name}</p>
					</div>
				</div>
			{/each}
		</div>
	{/if}
</div>
