import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionItemComponent } from './transaction-item/transaction-item.component';
import { TransactionListComponent } from './transaction-list/transaction-list.component';

const routes: Routes = [
  {
    path: 'all',
    component: TransactionListComponent
  },
  {
    path: ':itemid',
    component: TransactionItemComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TransactionRoutingModule {}
