import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  TokenService,
  ApiService,
  UserService,
  ProductService,
  TransactionService
} from './services';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpTokenInterceptor } from './interceptors';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpTokenInterceptor, multi: true },
    TokenService,
    ApiService,
    UserService,
    ProductService,
    TransactionService
  ]
})
export class CoreModule { }