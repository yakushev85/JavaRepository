import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  ApiService,
  UserService,
  ProductService,
  TransactionService
} from './services';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
  ],
  providers: [
    ApiService,
    UserService,
    ProductService,
    TransactionService
  ]
})
export class CoreModule { }
