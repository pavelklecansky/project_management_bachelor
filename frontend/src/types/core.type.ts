import type {User} from "./authentication.type";

export interface ErrorMessage {
    timestamp: Date;
    status: number;
    error: string;
    message: string;
    path: string;
}

export interface Project {
    id: string;
    name: string;
    description: string;
    startDate: Date;
    endDate: Date;
    tasks: Task[];
    comments: Comment[];
    members: User[];
    memberGroups: Group[];
}

export type Group = {
    id: string;
    name: string;
    members: GroupMember[];
}

export interface GroupMember {
    id: string;
    position: string;
    user: User;
}

export interface Comment {
    id: string;
    text: string,
    createdDate: Date;
    user: User;
}

export interface Organization {
    id: string;
    name: string;
    email: string;
    ico: string;
    phoneNumber: string;
    note: string;
}

export interface Phase {
    id: string;
    name: string;
    note: string;
    startDate: Date;
    endDate: Date;
}

export interface Schedule {
    id: string;
    project: Project;
    rows: Row[];
    tasks: ScheduleTask[];
}

export interface Row {
    id: number;
    realId?: string;
    label: string;
}

export interface ScheduleTask {
    id: number;
    realId?: string;
    label: string;
    resourceId: number;
    fromDate: Date;
    toDate: Date;
}

export interface Budget {
    id: string;
    project: Project;
    budgetCategories: BudgetCategory[];
}

export interface BudgetCategory {
    id: string;
    label: string;
    budget: number;
    items: BudgetItem[];
}

export interface BudgetItem {
    id: string;
    label: string;
    budget: number;
}

export interface Outcome {
    id: string;
    name: string;
    description: string;
    startDate: Date;
    endDate: Date;
    phase: Phase;
    outcomeCategory: OutcomeCategory;
    results: Result;
}

export interface OutcomeCategory {
    id: string;
    name: string;
    description: string;
}

export interface Result {
    id: string;
    name: string;
    description: string;
}

export interface Task {
    id: string;
    name: string;
    description: string;
    startDate: Date;
    endDate: Date;
    priority: string;
    status: string;
    assigned: User;
    assignedForGroup: Group;
    phase: Phase;
    progress: number;
    project: string;
    comments: Comment[];
}

export interface Priority {
    value: string;
    label: string;
}

export interface Status {
    value: string;
    label: string;
}