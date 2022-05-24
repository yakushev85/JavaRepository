import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

import { UserService } from '../services';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
  constructor(private userService: UserService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let headersConfig;

    const token = this.userService.loadFromStorage()?.token;

    if (token) {
      headersConfig = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Expose-Headers': 'x-csrf-token',
        'x-csrf-token': token
      };
    } else {
      headersConfig = {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      }
    }

    const request = req.clone({ setHeaders: headersConfig });

    return next.handle(request);
  }
}
