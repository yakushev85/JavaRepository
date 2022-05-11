import { Injectable } from '@angular/core';
import { map, distinctUntilChanged } from 'rxjs/operators';
import { Observable, BehaviorSubject, ReplaySubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { ApiService } from './api.service';
import { User } from '../models';
import { TokenService } from './token.service';

@Injectable()
export class UserService {
  private currentUserSubject = new BehaviorSubject<User>({} as User);
  public currentUser = this.currentUserSubject.asObservable().pipe(distinctUntilChanged());

  private isAuthenticatedSubject = new ReplaySubject<boolean>(1);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();

  private isAdminSubject = new ReplaySubject<boolean>(1);
  public isAdmin = this.isAdminSubject.asObservable();

  constructor (
    private tokenService: TokenService,
    private apiService: ApiService
  ) {}

  login(credentials: any): Observable<User> {
    return this.apiService.post('/login', credentials)
      .pipe(map(
      data => {
        this.setAuth(data);
        return data;
      }
    ));
  }

  signup(credentials: any): Observable<User> {
    return this.apiService.post('/signup', credentials)
      .pipe(map(
      data => {
        this.setAuth(data);
        return data;
      }
    ));
  }

  resetPassword(payload: any): Observable<User> {
    return this.apiService.post('/users/password', payload);
  }

  getAll(): Observable<User[]> {
    return this.apiService.get('/users/all');
  }

  getItem(itemid: number): Observable<User> {
    return this.apiService.get(`/users/${itemid}`);
  }

  add(payload: any): Observable<User> {
    return this.apiService.post(`/users/`, payload);
  }

  update(payload: any): Observable<User> {
    return this.apiService.put(`/users/`, payload);
  }

  logout() {
    this.purgeAuth();
    this.apiService.get('/logout').subscribe(
      data => {
        console.log(`logout:`, data);
      }
    );
  }


  populate() {
    this.purgeAuth();
  }

  setAuth(user: User) {
    this.tokenService.saveToken(user.token)
    this.currentUserSubject.next(user);
    this.isAuthenticatedSubject.next(true);
    this.isAdminSubject.next(user.role?.toLowerCase() == 'admin');
  }

  purgeAuth() {
    this.tokenService.destroyToken();
    this.currentUserSubject.next({} as User);
    this.isAuthenticatedSubject.next(false);
    this.isAdminSubject.next(false);
  }
}
