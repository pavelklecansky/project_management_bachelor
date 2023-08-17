<!-- routify:options query-params-is-page -->
<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { getContext, onMount } from 'svelte';
	import {
		deleteFile,
		deleteFolder,
		downloadFileInBrowser,
		listFiles,
		uploadFile
	} from '$lib/storage.service';
	import Document from 'carbon-icons-svelte/lib/Document.svelte';
	import Folder from 'carbon-icons-svelte/lib/Folder.svelte';
	import OveflowMenu from '$lib/components/core/OveflowMenu.svelte';
	import { error, success } from '$lib/notification';
	import UploadButton from '$lib/components/core/UploadButton.svelte';
	import EditFilePopup from '$lib/components/storage/EditFilePopup.svelte';
	import CreateButton from '$lib/components/core/CreateButton.svelte';

	let filesInfo = [];
	let files = [];
	const urlSearchParams = new URLSearchParams(window.location.search);
	const { open } = getContext('simple-modal');
	let id = $page.params.id;
	let loaded = false;

	$: queryParams = urlSearchParams.get('path') || '';

	onMount(async () => {
		await loadFilesInfo();
		loaded = true;
	});

	const download = async (name) => {
		await downloadFileInBrowser(id, name, queryParams);
	};

	const copyFileUrlToClipboard = async (name) => {
		const url = `${window.location.href}/download?path=${queryParams}&name=${name}`;
		await navigator.clipboard.writeText(url);
		success('File url was copied to clipboard');
	};

	const deleteF = async (name) => {
		const [successMessage, errorMessage] = await deleteFile(id, name, queryParams);
		if (!successMessage || errorMessage) {
			error(errorMessage);
		} else {
			success(successMessage);
		}
		await loadFilesInfo();
	};

	const deleteDir = async (name) => {
		const [successMessage, errorMessage] = await deleteFolder(id, name, queryParams);
		if (!successMessage || errorMessage) {
			error(errorMessage);
		} else {
			success(successMessage);
		}
		await loadFilesInfo();
	};

	const loadFilesInfo = async () => {
		const [success, errorMessage] = await listFiles(id, queryParams);
		if (!success || errorMessage) {
			goto(`/project/${$page.params}`);
		}
		filesInfo = success!;
		filesInfo.forEach(function (element) {
			element.hidden = true;
		});
	};

	const renameFileModal = async (oldName: string) => {
		await open(
			EditFilePopup,
			{ oldName, id, queryParams },
			{},
			{
				onClosed: async () => {
					await loadFilesInfo();
				}
			}
		);
	};

	$: if (files.length === 1) {
		const formData = new FormData();
		formData.append('file', files[0]);
		uploadFile(id, formData, queryParams)
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

	function removeLastFolderPath(queryParams: string) {
		return queryParams.substring(0, queryParams.lastIndexOf('/'));
	}
</script>

<div class="flex justify-between w-full mb-2">
	<div>
		{#if queryParams}
			<CreateButton
				text="🔼"
				on:click={() => {
					goto(`${$page.path}?path=${removeLastFolderPath(queryParams)}`);
				}}
			/>
		{/if}
	</div>

	<div>
		<CreateButton
			text="New folder"
			on:click={() => {
				goto(`./create-folder?path=${queryParams}`);
			}}
		/>
		<UploadButton text={'Add new file'} bind:files />
	</div>
</div>

{#if loaded}
	<div class="flex flex-wrap m-4 gap-5">
		{#each filesInfo as file}
			{#if file.folder}
				<div
					class="w-1/2 sm:w-1/3 lg:w-1/5 relative rounded-md pt-8 pb-5 text-center max-w-sm px-4 py-3 bg-white shadow-md box-border flex flex-col items-center cursor-pointer"
					title={file.name}
					on:click={() => {
						goto(`${$page.path}?path=${queryParams + `/${file.name}`}`);
					}}
				>
					<div on:click|stopPropagation={() => {}}>
						<OveflowMenu bind:hidden={file.hidden}>
							<span
								class="block cursor-pointer px-4 py-2 text-sm leading-5 text-red-600 font-bold hover:bg-gray-100 hover:text-red-700"
								on:click={() => {
									deleteDir(file.name);
									file.hidden = true;
								}}>DELETE FOLDER</span
							>
							<span
								class="block cursor-pointer px-4 py-2 text-sm leading-5 text-green-600 font-bold hover:bg-gray-100 hover:text-green-700"
								on:click={() => {
									renameFileModal(file.name);
									file.hidden = true;
								}}>EDIT FOLDER NAME</span
							>
						</OveflowMenu>
					</div>

					<div>
						<Folder class="w-32 h-32 fill-current" />
					</div>

					<div class="w-5/6">
						<p class="truncate">{file.name}</p>
					</div>
				</div>
			{:else}
				<div
					class="w-1/2 sm:w-1/3 lg:w-1/5 relative rounded-md pt-8 pb-5 text-center max-w-sm px-4 py-3 bg-white shadow-md box-border flex flex-col items-center cursor-pointer"
					on:click={() => download(file.name)}
					title={file.name}
				>
					<div on:click|stopPropagation={() => {}}>
						<OveflowMenu bind:hidden={file.hidden}>
							<span
								class="block cursor-pointer px-4 py-2 text-sm leading-5 text-red-600 font-bold hover:bg-gray-100 hover:text-red-700"
								on:click={() => {
									deleteF(file.name);
									file.hidden = true;
								}}>DELETE FILE</span
							>
							<span
								class="block cursor-pointer px-4 py-2 text-sm leading-5 text-green-600 font-bold hover:bg-gray-100 hover:text-green-700"
								on:click={() => {
									renameFileModal(file.name);
									file.hidden = true;
								}}>EDIT FILE NAME</span
							>
							<span
								class="block cursor-pointer px-4 py-2 text-sm leading-5 text-green-600 font-bold hover:bg-gray-100 hover:text-green-700"
								on:click={() => {
									copyFileUrlToClipboard(file.name);
									file.hidden = true;
								}}>COPY FILE URL</span
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
			{/if}
		{/each}
	</div>
{/if}
