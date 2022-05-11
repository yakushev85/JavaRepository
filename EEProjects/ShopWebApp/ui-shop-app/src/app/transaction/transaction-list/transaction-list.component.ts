import { Component, OnInit } from '@angular/core';
import { Product, ProductService, Transaction, TransactionService, UserService } from 'src/app/core';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  transactions: Transaction[] = [];
  products: Product[] = [];
  isAdmin = false;

  constructor(
    private transactionService: TransactionService,
    private productService: ProductService,
    private userService: UserService
    ) { }

  ngOnInit(): void {
    this.productService.getAll().subscribe(
      (value) => {
        if (value) {
          this.products = value;
        }
      }
    );

    this.transactionService.getAll().subscribe(
      (value) => {
        if (value) {
          this.transactions = value;
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
