export enum Role {
    ADMIN = 'ADMIN',
    MEMBER = 'MEMBER',
}

export interface User {
    id: string;
    username: string;
    email: string;
    quote: string;
    avatar: string;
    role: Role;
    dob: Date;
}
