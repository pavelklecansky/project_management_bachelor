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
    import type {Phase, Project} from "$lib/types/core.type";
    import {createPhase} from "$lib/phase.service";
    import ModalWindow from "$lib/components/modal/ModalWindow.svelte";
    import {transferify} from "$lib/utils";
    import {onMount} from "svelte";
    import {getProject} from "$lib/project.service";

    let id = $page.params.id;
    let project = {} as Project;

    onMount(async () => {
        let id = $page.params.id;
        const [success, errorMessage] = await getProject(id);
        project = success!;
    });

    const { form, handleSubmit } = createForm({
        initialValues: {
            phase: {} as Phase,
            startDate: "",
            endDate: "",
        },

        onSubmit: async (values) => {
            values.phase.startDate = new Date(values.startDate);
            values.phase.endDate = new Date(values.endDate);
            const [createdSuccess, createdError] = await createPhase(
                id,
                transferify(values.phase)
            );
            if (createdError) {
                error(createdError);
                goto(`/project/${id}/phases`);
            } else {
                success(createdSuccess);
                goto(`/project/${id}/phases`);
            }
        },
    });
</script>

<ModalWindow outsideClickGotoPath={`/project/${id}/phases`} {handleSubmit}>
    <div class="px-4 mb-4">
        <h2 class="text-3xl font-medium">Create new phase</h2>
        <p>
            <span class="font-bold">Project: </span>Start date:
            {new Date(project.startDate).toLocaleDateString("cs")}
            | End date:
            {new Date(project.endDate).toLocaleDateString("cs")}
        </p>
        <p>
           
        </p>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Name<span class="text-red-600">*</span>
            </p>
            <TextInput placeholder="Name" bind:value={$form.phase.name} />
        </div>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Description<span class="text-red-600">*</span>
            </p>
            <TextArea placeholder="Description" bind:value={$form.phase.note} />
        </div>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Start date<span class="text-red-600">*</span>
            </p>
            <DateInput title={"enter a start date for phases, the start cannot be before the start of the project"} bind:value={$form.startDate} />
        </div>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                End date<span class="text-red-600">*</span>
            </p>
            <DateInput title={"enter a end date for phases"} bind:value={$form.endDate} />
        </div>
    </div>
    <div
        class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
    >
        <SubmitButton text="Create" />
        <CloseButton text="Close" on:click={() => goto(`/project/${id}/phases`)} />
    </div>
</ModalWindow>
