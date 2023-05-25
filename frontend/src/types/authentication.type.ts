import type {Organization} from "./core.type";
import type {Role} from "./role.enum";

export interface User{
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    emailVerified: boolean;
    phoneNumber: string;
    note: string;
    roles: Role[];
    organizations: Organization[];
}