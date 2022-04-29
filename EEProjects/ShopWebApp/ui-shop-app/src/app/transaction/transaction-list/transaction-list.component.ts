import { Component, OnInit } from '@angular/core';
import { Product, ProductService, Transaction, TransactionService } from 'src/app/core';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  transactions: Transaction[] = [];
  products: Product[] = [];

  constructor(
    private transactionService: TransactionService,
    private productService: ProductService
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
  }

  getProductNameById(productId: number): string {
    for (let product of this.products) {
      if (product.id == productId) {
        return product.name;
      }
    }

    return "";
  }

}
