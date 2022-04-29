import { Component, OnInit } from '@angular/core';
import { Product, ProductService } from 'src/app/core';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getAll().subscribe(
      (value) => {
        if (value) {
          this.products = value;
        }
      }
    );
  }

}
