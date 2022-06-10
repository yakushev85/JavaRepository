import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageData } from '../models/page-data.model';
import { ApiService } from './api.service';

@Injectable()
export class CommentService {
    constructor (
        private apiService: ApiService
      ) {}
    
    getAll(page = 0, size = 10): Observable<PageData> {
        return this.apiService.get(`/comments?page=${page}&size=${size}`);
    }

    getAllbyArticleId(page = 0, size = 10, articleId: number): Observable<PageData> {
        return this.apiService.get(`/comments?articleId=${articleId}&page=${page}&size=${size}`);
    }

    getItem(itemId: number): Observable<Comment> {
        return this.apiService.get(`/comments/${itemId}`);
    }

    add(item: any): Observable<Comment> {
        return this.apiService.post(`/comments`, item);
    }
}