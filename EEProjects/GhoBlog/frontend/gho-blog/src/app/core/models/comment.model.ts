import { Article } from "./article.model";
import { Profile } from "./profile.model";

export interface Comment {
    id: number;
    content: string;
    article: Article;
    createdAt: Date;
    author: Profile;
}