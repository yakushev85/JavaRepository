import { Injectable } from '@angular/core';
import { map, distinctUntilChanged } from 'rxjs/operators';
import { Observable, BehaviorSubject, ReplaySubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { ApiService } from './api.service';
import { Product } from '../models/product.model';


@Injectable()
export class ProductService {
  constructor (
    private apiService: ApiService,
    private http: HttpClient,
  ) {}

  getAll(): Observable<Product[]> {
    return this.apiService.get('/products/all');
  }

  getItem(itemId: number): Observable<Product> {
    return this.apiService.get(`/products/${itemId}`);
  }
}
