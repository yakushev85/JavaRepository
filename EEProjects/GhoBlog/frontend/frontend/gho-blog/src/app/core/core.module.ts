import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleService } from './services/article.service';
import { CommentService } from './services/comment.service';
import { ProfileService } from './services/profile.service';
import { ApiService } from './services/api.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    ApiService,
    ArticleService,
    CommentService,
    ProfileService
  ]
})
export class CoreModule { }
