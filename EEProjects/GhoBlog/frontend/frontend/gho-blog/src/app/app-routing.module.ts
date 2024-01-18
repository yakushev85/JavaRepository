import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '@auth0/auth0-angular';
import { ArticleItemResolver } from './article/article-item/article-item-resolver.service';
import { ArticleItemComponent } from './article/article-item/article-item.component';
import { ArticleListComponent } from './article/article-list/article-list.component';
import { ArticleNewComponent } from './article/article-new/article-new.component';

const routes: Routes = [
  {path: 'articles', component: ArticleListComponent},
  {path: 'articles/new', component: ArticleNewComponent, canActivate: [AuthGuard]},
  {path: 'articles/:itemid', component: ArticleItemComponent, resolve: { article: ArticleItemResolver }},
  {path: '', redirectTo: 'articles', pathMatch: 'full'},
  {path: '**', redirectTo: 'articles', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
