import { Component, OnInit } from '@angular/core';
import { Product, ProductService, User, UserService } from 'src/app/core';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  isAdmin = false;

  constructor(
    private productService: ProductService,
    private userService: UserService) { }

  ngOnInit(): void {
    this.productService.getAll().subscribe(
      (value) => {
        if (value) {
          this.products = (value.content as Product[]);
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
