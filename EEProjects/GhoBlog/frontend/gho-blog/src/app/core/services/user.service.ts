import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, map } from 'rxjs';

import { ApiService } from './api.service';
import { User } from '../models/user.model';
import { ProfileService } from './profile.service';

@Injectable()
export class UserService {
  private currentUserSubject = new BehaviorSubject<User>({} as User);
  public currentUser = this.currentUserSubject.asObservable();

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();

  constructor (
    private apiService: ApiService,
    private profileService: ProfileService
  ) {}

  login(credentials: any): Observable<User> {
    // TODO
    return this.profileService.getByEmail("test@test.com")
    .pipe(map( (value) => {
        let resolvedUser = { email: "test@test.com", name: "test" };
        this.setAuth(resolvedUser);

        return resolvedUser;
    }));
  }

  signup(credentials: any): Observable<User> {
    // TODO
    return this.profileService.add({
        email: "test@test.com",
        name: "test"
    }).pipe(map( (value) => {
      let resolvedUser = { email: "test@test.com", name: "test" };
      this.setAuth(resolvedUser);

      return resolvedUser;
    }));
  }

  logout() {
    this.purgeAuth();
  }

  populate() {
    let storedUser = this.loadFromStorage();

    if (storedUser) {
      this.setAuth(storedUser);
    } else {
      this.purgeAuth();
    }
  }

  setAuth(user: User) {
    sessionStorage.setItem("user_data", JSON.stringify(user));
    this.currentUserSubject.next(user);
    this.isAuthenticatedSubject.next(true);
  }

  purgeAuth() {
    sessionStorage.removeItem("user_data");
    this.currentUserSubject.next({} as User);
    this.isAuthenticatedSubject.next(false);
  }

  loadFromStorage(): User | null {
    let storedRawData = sessionStorage.getItem("user_data");

    if (storedRawData) {
      return JSON.parse(storedRawData);
    } else {
      return null;
    }
  }
}