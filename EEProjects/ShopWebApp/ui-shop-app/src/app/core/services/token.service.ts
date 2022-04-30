import { Injectable } from '@angular/core';


@Injectable()
export class TokenService {

  getToken(): string {
    return sessionStorage['_csrf'];
  }

  saveToken(token: string) {
    sessionStorage['_csrf'] = token;
  }

  destroyToken() {
    sessionStorage.removeItem('_csrf');
  }

}
