<script>
    import {createEventDispatcher} from 'svelte';
    import {time} from "$lib/utils";
    import CreateButton from "../core/CreateButton.svelte";

    export let currentStart = time('06:00');
    export let currentEnd = time('18:00');
    export let scheduleId;
    export let id;
    const dispatch = createEventDispatcher();

    function onUpdateOptions(options) {
        dispatch('updateOptions', options);
    }

    function onSetDayView() {
        onUpdateOptions({
            fitWidth: true,
            columnUnit: 'minute',
            columnOffset: 15,
            from: currentStart,
            to: currentEnd,
            minWidth: 1000,
            headers: [{ unit: 'day', format: 'DD.MM.YYYY' }, { unit: 'hour', format: 'HH' }]
        });
    }
    function onSetWeekView() {
        onUpdateOptions({
            fitWidth: false,
            columnUnit: 'hour',
            columnOffset: 1,
            from: currentStart.clone().startOf('week'),
            to: currentStart.clone().endOf('week'),
            minWidth: 5000,
            headers: [{ unit: 'month', format: 'MMMM YYYY', sticky: true }, { unit: 'day', format: 'ddd DD', sticky: true }]
        });
    }

    function onSetMonthView() {
        onUpdateOptions({
            fitWidth: false,
            columnUnit: 'hour',
            columnOffset: 1,
            from: currentStart.clone().startOf('month'),
            to: currentStart.clone().endOf('month'),
            minWidth: 5000,
            headers: [{ unit: 'month', format: 'MMMM YYYY', sticky: true }, { unit: 'day', format: 'ddd DD', sticky: true }]
        });
    }
    function onSetYearView() {
        onUpdateOptions({
            fitWidth: false,
            columnUnit: 'day',
            columnOffset: 1,
            from: currentStart.clone().startOf('year'),
            to: currentStart.clone().endOf('year'),
            minWidth: 5000,
            headers: [{ unit: 'month', format: 'MMMM YYYY', sticky: true }]
        });
    }
    function onSetNextDay() {
        currentStart.add(1, 'day');
        currentEnd.add(1, 'day');
        onUpdateOptions({
            from: currentStart,
            to: currentEnd
        });
    }
    function onSetPreviousDay() {
        currentStart.subtract(1, 'day');
        currentEnd.subtract(1, 'day');
        onUpdateOptions({
            from: currentStart,
            to: currentEnd
        });
    }
</script>

<div class="flex justify-between m-2">
    <div>
        <CreateButton color={"green"} text={"<"} on:click={onSetPreviousDay}/>
        <CreateButton color={"green"} text={"Day view"} on:click={onSetDayView}/>
        <CreateButton color={"green"} text={">"} on:click={onSetNextDay}/>

        <CreateButton color={"green"} text={"Week view"} on:click={onSetWeekView}/>
        <CreateButton color={"green"} text={"Month view"} on:click={onSetMonthView}/>
        <CreateButton color={"green"} text={"Year view"} on:click={onSetYearView}/>
    </div>
    <div>

        <a class="mr-4" href={`./${id}/create-task/${scheduleId}`}>
            <CreateButton text={"Create new task"}/>
        </a>
        <a href={`./${id}/create-row/${scheduleId}`}>
            <CreateButton text={"Create new row"}/>
        </a>
        <a  href={`./${id}/delete-row/${scheduleId}`}>
            <CreateButton color="red" text={"Delete Row"}/>
        </a>
        <a  href={`./${id}/edit-row/${scheduleId}`}>
            <CreateButton color="green" text={"Edit Row"}/>
        </a>
    </div>
</div>