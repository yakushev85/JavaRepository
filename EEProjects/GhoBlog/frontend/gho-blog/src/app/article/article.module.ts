import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleListComponent } from './article-list/article-list.component';
import { ArticleItemComponent } from './article-item/article-item.component';
import { ArticleNewComponent } from './article-new/article-new.component';

@NgModule({
  declarations: [
    ArticleListComponent,
    ArticleItemComponent,
    ArticleNewComponent
  ],
  imports: [
    CommonModule
  ]
})
export class ArticleModule { }
