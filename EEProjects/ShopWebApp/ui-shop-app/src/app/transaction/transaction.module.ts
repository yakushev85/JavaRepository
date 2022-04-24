import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransactionListComponent } from './transaction-list/transaction-list.component';
import { TransactionItemComponent } from './transaction-item/transaction-item.component';



@NgModule({
  declarations: [
    TransactionListComponent,
    TransactionItemComponent
  ],
  imports: [
    CommonModule
  ]
})
export class TransactionModule { }
