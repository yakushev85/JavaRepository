import { Component, OnInit } from '@angular/core';
import { Transaction, TransactionService, UserService } from 'src/app/core';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  transactions: Transaction[] = [];
  isAdmin = false;

  constructor(
    private transactionService: TransactionService,
    private userService: UserService
    ) { }

  ngOnInit(): void {
    this.transactionService.getAll().subscribe(
      (value) => {
        if (value) {
          this.transactions = (value.content as Transaction[]);
        }
      }
    );

    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

}
