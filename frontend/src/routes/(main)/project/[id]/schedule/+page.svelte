<script lang="ts">
	import { onMount, setContext } from 'svelte';
	import {
		MomentSvelteGanttDateAdapter,
		SvelteGantt,
		SvelteGanttDependencies,
		SvelteGanttTable
	} from 'svelte-gantt';
	import moment from 'moment';
	import { getScheduleByProject } from '$lib/schedule.service';
	import { backendScheduleTaskToFrontend } from '$lib/utils';
	import type { Schedule } from '$lib/types/core.type';
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { error } from '$lib/notification';
	import ScheduleNav from '$lib/components/schedule/ScheduleNav.svelte';
	import { writable } from 'svelte/store';
	import GanttOptions from '$lib/components/schedule/GanttOptions.svelte';

	function time(input) {
		return moment(input, 'HH:mm');
	}

	let optionsStream = new writable({});

	function onChangeOptionsNav(event) {
		const opts = event.detail;
		$optionsStream = opts;
		optionsStream.set(opts);
	}

	setContext('options', { optionsStream });

	let id = $page.params.id;
	let schedule = {
		rows: [],
		tasks: []
	} as Schedule;

	const currentStart = time('08:00');
	const currentEnd = time('18:00');

	let options = {
		dateAdapter: new MomentSvelteGanttDateAdapter(moment),
		rows: schedule.rows,
		tasks: schedule.tasks,
		dependencies: [],
		timeRanges: [],
		columnUnit: 'day',
		columnOffset: 7,
		magnetUnit: 'day',
		magnetOffset: 1,
		rowHeight: 52,
		rowPadding: 6,
		headers: [
			{ unit: 'day', format: 'DD.MM.YYYY' },
			{ unit: 'hour', format: 'HH' }
		],
		fitWidth: true,
		minWidth: 800,
		from: currentStart,
		to: currentEnd,
		tableHeaders: [{ title: 'Project Schedule', property: 'label', width: 140, type: 'tree' }],
		tableWidth: 240,
		ganttTableModules: [SvelteGanttTable],
		ganttBodyModules: [SvelteGanttDependencies]
	};

	let gantt;
	onMount(async () => {
		const [successSchedule, errorMessage] = await getScheduleByProject(id);
		if (!successSchedule || errorMessage) {
			error(errorMessage);
			goto('/');
		}
		schedule = successSchedule;
		window.gantt = gantt = new SvelteGantt({
			target: document.getElementById('example-gantt-events'),
			props: options
		});

		options.rows = schedule.rows;
		options.tasks = backendScheduleTaskToFrontend(schedule.tasks);
	});

	function onChangeOptions(event) {
		const opts = event.detail;
		Object.assign(options, opts);
		gantt.$set(options);
	}
</script>

<ScheduleNav scheduleId={schedule.id} {id} on:updateOptions={onChangeOptionsNav} />
<div class="container">
	<div id="example-gantt-events" />
	<GanttOptions {options} on:change={onChangeOptions} />
</div>

<style>
	#example-gantt-events {
		flex-grow: 1;
		overflow: auto;
	}

	.container {
		display: flex;
		overflow: auto;
		flex: 1;
	}
</style>
