import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArticleItemComponent } from './article/article-item/article-item.component';
import { ArticleListComponent } from './article/article-list/article-list.component';
import { ArticleNewComponent } from './article/article-new/article-new.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'articles', component: ArticleListComponent},
  {path: 'articles/new', component: ArticleNewComponent},
  {path: 'articles/:id', component: ArticleItemComponent},
  {path: '', redirectTo: 'articles', pathMatch: 'full'},
  {path: '**', redirectTo: 'articles', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
