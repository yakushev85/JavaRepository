import { Injectable } from '@angular/core';
import { map, distinctUntilChanged } from 'rxjs/operators';
import { Observable, BehaviorSubject, ReplaySubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { ApiService } from './api.service';
import { Transaction } from '../models/transaction.model';


@Injectable()
export class TransactionService {
  constructor (
    private apiService: ApiService,
    private http: HttpClient,
  ) {}

  getAll(): Observable<Transaction[]> {
    return this.apiService.get('/transactions/all')
    .pipe(map(
      data => {
        return data;
      }
    ));
  }

  getItem(itemId: number): Observable<Transaction> {
    return this.apiService.get(`/transactions/${itemId}`)
    .pipe(map(
      data => {
        return data;
      }
    ));
  }
}
