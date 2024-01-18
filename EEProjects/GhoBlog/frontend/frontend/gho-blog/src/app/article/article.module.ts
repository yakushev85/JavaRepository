import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleListComponent } from './article-list/article-list.component';
import { ArticleItemComponent } from './article-item/article-item.component';
import { ArticleNewComponent } from './article-new/article-new.component';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ArticleItemResolver } from './article-item/article-item-resolver.service';

@NgModule({
  declarations: [
    ArticleListComponent,
    ArticleItemComponent,
    ArticleNewComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    ArticleItemResolver
  ]
})
export class ArticleModule { }
