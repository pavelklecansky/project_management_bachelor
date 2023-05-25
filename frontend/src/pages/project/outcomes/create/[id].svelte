<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import {error, success} from "../../../../lib/notification";
    import SubmitButton from "../../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../../components/core/CloseButton.svelte";
    import TextInput from "../../../../components/core/TextInput.svelte";
    import {createForm} from "svelte-forms-lib";
    import TextArea from "../../../../components/core/TextArea.svelte";
    import DateInput from "../../../../components/core/DateInput.svelte";
    import type {Outcome} from '../../../../types/core.type';
    import ModalWindow from "../../../../components/modal/ModalWindow.svelte";
    import {transferify} from '../../../../lib/utils';
    import {createOutcome} from '../../../../lib/outcomes.service';
    import PhaseSelectInput from "../../../../components/phase/PhaseSelectInput.svelte";
    import OutcomeCategorySelectInput from "../../../../components/outcome-category/OutcomeCategorySelectInput.svelte";

    let id = $params.id;

    const {form, handleSubmit} = createForm({
        initialValues: {
            outcome: {} as Outcome,
            startDate: "",
            endDate: "",
        },

        onSubmit: async (values) => {
            values.outcome.startDate = new Date(values.startDate);
            values.outcome.endDate = new Date(values.endDate);
            values.outcome.phase = Object.keys(values.outcome.phase).length === 0 ? null : values.outcome.phase;
            values.outcome.outcomeCategory = Object.keys(values.outcome.outcomeCategory).length === 0 ? null : values.outcome.outcomeCategory;
            const [createdSuccess, createdError] = await createOutcome(
                transferify(values.outcome)
            );
            if (createdError) {
                error(createdError);
                $goto(`./../${id}`);
            } else {
                success(createdSuccess);
                $goto(`./../${id}`);
            }
        },
    });
</script>

<ModalWindow outsideClickGotoPath={`./../${id}`} {handleSubmit}>
    <div class="px-4 mb-4">
        <h2 class="text-3xl font-medium">Create new outcome</h2>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Name<span class="text-red-600">*</span>
            </p>
            <TextInput placeholder="Name" bind:value={$form.outcome.name}/>
        </div>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Description<span class="text-red-600">*</span>
            </p>
            <TextArea
                    placeholder="Description"
                    bind:value={$form.outcome.description}
            />
        </div>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Phase<span class="text-red-600">*</span>
            </p>
            <PhaseSelectInput title={"Test"} projectId={id} bind:value={$form.outcome.phase}/>
        </div>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Outcome Category
            </p>
            <OutcomeCategorySelectInput projectId={id} bind:value={$form.outcome.outcomeCategory}/>
        </div>

        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Start date<span class="text-red-600">*</span>
            </p>
            <DateInput title={"enter a start date for outcome, the start cannot be before the start of selected phase"} bind:value={$form.startDate}/>
        </div>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                End date<span class="text-red-600">*</span>
            </p>
            <DateInput title={"enter a end date for outcome"} bind:value={$form.endDate}/>
        </div>
    </div>
    <div
            class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
    >
        <SubmitButton text="Create"/>
        <CloseButton text="Close" on:click={() => $goto(`./../${id}`)}/>
    </div>
</ModalWindow>
