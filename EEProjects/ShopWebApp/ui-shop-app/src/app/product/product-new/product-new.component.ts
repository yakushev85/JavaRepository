import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserService, ProductService } from 'src/app/core';

@Component({
  selector: 'app-product-new',
  templateUrl: './product-new.component.html',
  styleUrls: ['./product-new.component.css']
})
export class ProductNewComponent implements OnInit {
  currentUser: User | undefined;
  productAdminForm: FormGroup  = this.fb.group({
    name: ['', Validators.required],
    price: ['', Validators.required]
  });
  isSubmitting = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private productService: ProductService,
    private fb: FormBuilder
    ) { }

  ngOnInit(): void {
    this.userService.currentUser.subscribe(
      (userData) => {
        this.currentUser = userData;
      }
    );
  }

  submitAdminForm() {
    if (!this.currentUser?.id) {
      return;
    }

    this.isSubmitting = true;

    let newProductItem = (this.productAdminForm.value as { name: string, price: string });

    this.productService.add(newProductItem)
    .subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigateByUrl('/products/all');
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }
}
