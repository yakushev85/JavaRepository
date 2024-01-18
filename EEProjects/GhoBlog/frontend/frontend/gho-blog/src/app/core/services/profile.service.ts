import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Profile } from '../models/profile.model';
import { ApiService } from './api.service';

@Injectable()
export class ProfileService {
    constructor (
        private apiService: ApiService
      ) {}

    getByEmail(email: string): Observable<Profile> {
        return this.apiService.get(`/private/api/profiles/email/${email}`);
    }

    add(item: any): Observable<Profile> {
        return this.apiService.post(`/private/api/profiles`, item);
    }
}