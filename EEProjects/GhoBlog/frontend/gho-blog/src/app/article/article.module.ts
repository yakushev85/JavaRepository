import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleListComponent } from './article-list/article-list.component';
import { ArticleItemComponent } from './article-item/article-item.component';
import { CommentListComponent } from './comment-list/comment-list.component';
import { CommentNewComponent } from './comment-new/comment-new.component';
import { ArticleMainComponent } from './article-main/article-main.component';



@NgModule({
  declarations: [
    ArticleListComponent,
    ArticleItemComponent,
    CommentListComponent,
    CommentNewComponent,
    ArticleMainComponent
  ],
  imports: [
    CommonModule
  ]
})
export class ArticleModule { }
