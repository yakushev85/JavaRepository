import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransactionListComponent } from './transaction-list/transaction-list.component';
import { TransactionItemComponent } from './transaction-item/transaction-item.component';
import { TransactionRoutingModule } from './transaction-routing.module';
import { TransactionItemResolver } from './transaction-item/transaction-item-resolver.component';


@NgModule({
  declarations: [
    TransactionListComponent,
    TransactionItemComponent
  ],
  imports: [
    CommonModule,
    TransactionRoutingModule
  ],
  providers: [
    TransactionItemResolver
  ]
})
export class TransactionModule { }
