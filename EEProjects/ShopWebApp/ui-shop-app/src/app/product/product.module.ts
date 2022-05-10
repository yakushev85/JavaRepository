import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductItemComponent } from './product-item/product-item.component';
import { ProductRoutingModule } from './product-routing.module';
import { ProductItemResolver } from './product-item/product-item-resolver.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductNewComponent } from './product-new/product-new.component';



@NgModule({
  declarations: [
    ProductListComponent,
    ProductItemComponent,
    ProductNewComponent
  ],
  imports: [
    CommonModule,
    ProductRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    ProductItemResolver
  ]
})
export class ProductModule { }
