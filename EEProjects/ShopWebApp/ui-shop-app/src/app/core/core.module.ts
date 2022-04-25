import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  ApiService,
  UserService
} from './services';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
  ],
  providers: [
    ApiService,
    UserService
  ]
})
export class CoreModule { }
