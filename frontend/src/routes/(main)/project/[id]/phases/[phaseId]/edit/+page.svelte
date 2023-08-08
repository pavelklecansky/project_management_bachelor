<script lang="ts">
    import { page } from '$app/stores';
    import { goto } from '$app/navigation';
    import {error, success} from "$lib/notification";
    import SubmitButton from "$lib/components/core/SubmitButton.svelte";
    import CloseButton from "$lib/components/core/CloseButton.svelte";
    import TextInput from "$lib/components/core/TextInput.svelte";
    import {createForm} from "svelte-forms-lib";
    import TextArea from "$lib/components/core/TextArea.svelte";
    import DateInput from "$lib/components/core/DateInput.svelte";
    import type {Phase} from '$lib/types/core.type';
    import {getPhase, updatePhase} from '$lib/phase.service';
    import ModalWindow from "$lib/components/modal/ModalWindow.svelte";
    import {dateISOFormat, transferify} from '$lib/utils';
    import {onMount} from 'svelte';

    let id = $page.params.phaseId;

    const {form, handleSubmit} = createForm({
        initialValues: {
            phase: {} as Phase,
            startDate: "",
            endDate: "",
        },

        onSubmit: async (values) => {
            values.phase.startDate = new Date(values.startDate);
            values.phase.endDate = new Date(values.endDate);
            const [createdSuccess, createdError] = await updatePhase(
                transferify(values.phase)
            );
            if (createdError) {
                error(createdError);
                goto(`./../${id}`);
            } else {
                success(createdSuccess);
                goto(`./../${id}`);
            }
        },
    });

    onMount(async () => {
        const [success, errorMessage] = await getPhase(id);
        if (!success || errorMessage) {
            error(errorMessage);
            goto(`./../`);
        } else {
            $form.phase = success!;
            $form.endDate = dateISOFormat(success.endDate);
            $form.startDate = dateISOFormat(success.startDate);
        }
    });
</script>

<ModalWindow outsideClickGotoPath={`./../${id}`} {handleSubmit}>
    <div class="px-4 mb-4">
        <h2 class="text-3xl font-medium">Edit phase</h2>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Name
            </p>
            <TextInput placeholder="Name" bind:value={$form.phase.name}/>
        </div>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Description
            </p>
            <TextArea
                    placeholder="Description"
                    bind:value={$form.phase.note}
            />
        </div>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Start date
            </p>
            <DateInput bind:value={$form.startDate}/>
        </div>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                End date
            </p>
            <DateInput bind:value={$form.endDate}/>
        </div>
    </div>
    <div
            class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
    >
        <SubmitButton text="Create"/>
        <CloseButton text="Close" on:click={() => goto(`./../${id}`)}/>
    </div>
</ModalWindow>
