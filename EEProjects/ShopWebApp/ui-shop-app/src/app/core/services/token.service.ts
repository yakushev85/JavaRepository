import { Injectable } from '@angular/core';


@Injectable()
export class TokenService {

  getToken(): string {
    return window.localStorage['_csrf'];
  }

  saveToken(token: string) {
    window.localStorage['_csrf'] = token;
  }

  destroyToken() {
    window.localStorage.removeItem('_csrf');
  }

}
