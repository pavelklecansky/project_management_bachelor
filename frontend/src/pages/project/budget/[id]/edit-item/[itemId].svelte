<script lang="ts">
    import {goto, params} from "@roxi/routify";
    import type {BudgetCategory, BudgetItem} from '../../../../../types/core.type';
    import {createForm} from 'svelte-forms-lib';
    import {error, success} from '../../../../../lib/notification';
    import TextInput from "../../../../../components/core/TextInput.svelte";
    import SubmitButton from "../../../../../components/core/SubmitButton.svelte";
    import CloseButton from "../../../../../components/core/CloseButton.svelte";
    import ModalWindow from '../../../../../components/modal/ModalWindow.svelte';
    import {getItem, getItemCategory, updateItem} from '../../../../../lib/budget.service';
    import NumberInput from "../../../../../components/core/NumberInput.svelte";
    import {onMount} from 'svelte';
    import {getItemsSum} from '../../../../../lib/utils';

    let id = $params.id;
    let itemId = $params.itemId;
    let category: BudgetCategory;

    function isMoreThenBudget(item: BudgetItem) {
        const budgetItems = category.items.slice(0).filter(value => value.id != item.id);
        const itemsSum = getItemsSum(budgetItems) + item.budget;
        return itemsSum > category.budget;
    }

    const {form, handleSubmit} = createForm({
        initialValues: {
            item: {} as BudgetItem,
        },

        onSubmit: async (values) => {
            if (isMoreThenBudget(values.item)) {
                error("Item budget bigger then category budget.");
            } else {
                const [createdSuccess, createdError] = await updateItem(
                    values.item.id,
                    values.item
                );
                if (createdError) {
                    error(createdError);
                    $goto(`/project/budget/${id}`);
                } else {
                    success(createdSuccess);
                    $goto(`/project/budget/${id}`);
                }
            }
        },
    });

    onMount(async () => {
        const [success, errorMessage] = await getItem(itemId);
        if (!success || errorMessage) {
            error(errorMessage);
            $goto("./../");
        } else {
            $form.item = success!;
            const [successCategory, errorMessageCategory] = await getItemCategory(itemId);
            category = successCategory;
        }
    });
</script>

<ModalWindow outsideClickGotoPath={`/project/budget/${id}`} {handleSubmit}>
    <div class="px-4 mb-4">
        <h2 class="text-3xl font-medium">Create new budget item</h2>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Label
            </p>
            <TextInput placeholder="Name" bind:value={$form.item.label}/>
        </div>
        <div class="w-full mt-4 ">
            <p class="text-left text-sm font-medium text-gray-900 block mb-2">
                Budget
            </p>
            <NumberInput bind:value={$form.item.budget}/>
        </div>
    </div>
    <div
            class="flex mt-10 justify-between py-4 px-4 border-t border-gray-300 false"
    >
        <SubmitButton text="Create"/>
        <CloseButton text="Close" on:click={() => $goto(`/project/budget/${id}`)}/>
    </div>
</ModalWindow>
