import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable()
export class ProfileService {
    constructor (
        private apiService: ApiService
      ) {}

    getByEmail(email: string): Observable<Comment> {
        return this.apiService.get(`/profiles/search/findByEmail?email=${email}`);
    }

    add(item: any): Observable<Comment> {
        return this.apiService.post(`/profiles`, item);
    }
}