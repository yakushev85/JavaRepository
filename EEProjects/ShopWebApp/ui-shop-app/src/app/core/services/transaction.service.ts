import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Transaction } from '../models/transaction.model';
import { PageData } from '../models';


@Injectable()
export class TransactionService {
  constructor (
    private apiService: ApiService
  ) {}

  getAll(): Observable<PageData> {
    return this.apiService.get('/transactions/all');
  }

  getItem(itemId: number): Observable<Transaction> {
    return this.apiService.get(`/transactions/${itemId}`);
  }

  createItem(data: any): Observable<Transaction> {
    return this.apiService.post('/transactions/', data);
  }
}
