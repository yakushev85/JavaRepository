import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product, Transaction, TransactionService, User, UserService } from 'src/app/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {
  product: Product | undefined;
  currentUser: User | undefined;
  productForm : FormGroup = this.fb.group({
    description: ''
  });
  isSubmitting = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private transactionService: TransactionService,
    private fb: FormBuilder
    ) { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data) => {
        console.log("ProductItemComponent: ", data);
        this.product = (data as { product: Product }).product;
      }
    );

    this.userService.currentUser.subscribe(
      (userData) => {
        this.currentUser = userData;
      }
    );

    this.productForm = this.fb.group({
      description: ['', Validators.required]
    });
  }

  submitForm() {
    if (!this.product && this.currentUser?.id) {
      return;
    }

    this.isSubmitting = true;

    let transactionItem: Transaction = {
      id: 0,
      description: (this.productForm.value as { description: string}).description,
      productId: this.product?.id || 0,
      userId: this.currentUser?.id || 0,
      createdAt: ''
    };

    // post the changes
    this.transactionService.createItem(transactionItem)
    .subscribe({
      next: (data) => {
        this.router.navigateByUrl('/transactions/all');
      },
      complete: () => {
        this.isSubmitting = false;
      }
    });
  }
}
