import { Injectable, } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Article } from 'src/app/core/models/article.model';
import { ArticleService } from 'src/app/core/services/article.service';

@Injectable()
export class ArticleItemResolver implements Resolve<Article> {
  constructor(
    private articleService: ArticleService,
    private router: Router,
  ) {}

  resolve(
    route: ActivatedRouteSnapshot
  ): Observable<any> {

    return this.articleService.getItem(route.params['itemid'])
      .pipe(catchError((err) => this.router.navigateByUrl('/')));
  }
}