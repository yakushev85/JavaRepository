import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../models/article.model';
import { PageData } from '../models/page-data.model';
import { ApiService } from './api.service';

@Injectable()
export class ArticleService {
    constructor (
        private apiService: ApiService
      ) {}
    
      getAll(page = 0, size = 10): Observable<PageData> {
        return this.apiService.get(`/public/api/articles?page=${page}&size=${size}`);
      }
    
      getItem(itemId: number): Observable<Article> {
        return this.apiService.get(`/public/api/articles/${itemId}`);
      }
    
      add(item: any): Observable<Article> {
        return this.apiService.post(`/private/api/articles`, item);
      }
}