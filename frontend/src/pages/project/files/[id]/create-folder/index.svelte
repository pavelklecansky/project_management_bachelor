<!-- routify:options query-params-is-page -->
<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../../lib/notification";
    import {createForm} from 'svelte-forms-lib';
    import TextInput from "../../../../../components/core/TextInput.svelte";
    import SubmitButton from "../../../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../../../components/core/CloseButton.svelte";
    import ModalWindow from "../../../../../components/modal/ModalWindow.svelte";
    import {createFolder} from '../../../../../lib/storage.service';

    let id = $params.id;
    const urlSearchParams = new URLSearchParams(window.location.search);
    $: queryParams = urlSearchParams.get('path') || "";


    const {form, handleSubmit} = createForm({
        initialValues: {
            name: "",
        },

        onSubmit: async (values) => {
            const [createdSuccess, createdError] = await createFolder(
                id,
                values.name,
                queryParams
            );
            if (createdError) {
                error(createdError);
                $goto(`../?path=${queryParams}`);
            } else {
                success(createdSuccess);
                $goto(`../?path=${queryParams}`);
            }
        },
    });


</script>

<ModalWindow outsideClickGotoPath={`../?path=${queryParams}`} {handleSubmit}>
    <div class="px-4 mb-4">
        <h2 class="text-3xl font-medium">Create new folder</h2>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Name
            </p>
            <TextInput placeholder="Name" bind:value={$form.name}/>
        </div>
    </div>
    <div
            class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
    >
        <SubmitButton text="Create"/>
        <CloseButton text="Close" on:click={() => $goto(`../?path=${queryParams}`)}/>
    </div>
</ModalWindow>
