import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransactionListComponent } from './transaction-list/transaction-list.component';
import { TransactionItemComponent } from './transaction-item/transaction-item.component';
import { TransactionRoutingModule } from './transaction-routing.module';


@NgModule({
  declarations: [
    TransactionListComponent,
    TransactionItemComponent
  ],
  imports: [
    CommonModule,
    TransactionRoutingModule
  ]
})
export class TransactionModule { }