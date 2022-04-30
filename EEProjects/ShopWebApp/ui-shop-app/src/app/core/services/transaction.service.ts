import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Transaction } from '../models/transaction.model';


@Injectable()
export class TransactionService {
  constructor (
    private apiService: ApiService
  ) {}

  getAll(): Observable<Transaction[]> {
    return this.apiService.get('/transactions/all');
  }

  getItem(itemId: number): Observable<Transaction> {
    return this.apiService.get(`/transactions/${itemId}`);
  }

  createItem(data: Transaction): Observable<Transaction> {
    return this.apiService.post('/transactions/', data);
  }
}
