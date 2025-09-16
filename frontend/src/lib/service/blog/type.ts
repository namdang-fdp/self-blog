import { User } from '../account/type';
import { Genre } from '../genre/type';

export interface Blog {
    id: string;
    postedUser: User;
    title: string;
    summary: string;
    content: string;
    coverPath: string;
    genre: Genre[];
    publicationDate: Date;
    view: number;
    totalReact: number;
}
