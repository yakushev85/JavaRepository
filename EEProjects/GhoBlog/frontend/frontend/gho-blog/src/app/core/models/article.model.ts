import { Profile } from "./profile.model";

export interface Article {
    id: number;
    title: string;
    content: string;
    createdAt: Date;
    author: Profile;
}