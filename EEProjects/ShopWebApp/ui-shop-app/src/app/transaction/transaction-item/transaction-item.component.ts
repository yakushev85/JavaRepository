import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product, ProductService, Transaction, TransactionService, User, UserService } from 'src/app/core';

@Component({
  selector: 'app-transaction-item',
  templateUrl: './transaction-item.component.html',
  styleUrls: ['./transaction-item.component.css']
})
export class TransactionItemComponent implements OnInit {
  transaction: Transaction | undefined;
  product: Product | undefined;
  linkedUser: User | undefined;
  isSubmitting = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private productService: ProductService)
     { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data) => {
        console.log('TransactionItemComponent:', data);
        this.transaction = (data as { transaction: Transaction }).transaction;

        this.productService.getItem(this.transaction.product.id).subscribe(
          (value) => {
            this.product = value;
          }
        );
      }
    );
  }

}
