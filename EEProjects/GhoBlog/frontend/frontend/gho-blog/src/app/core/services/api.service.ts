import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable()
export class ApiService {
  private defaultHeaders = {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }

  constructor(
    private http: HttpClient,
  ) {}

  get(path: string): Observable<any> {
    return this.http.get(
      `${environment.apiUrl}${path}`,
      { headers: this.defaultHeaders }
      );
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http.put(
      `${environment.apiUrl}${path}`,
      JSON.stringify(body),
      { headers: this.defaultHeaders }
    );
  }

  post(path: string, body: Object = {}): Observable<any> {
    return this.http.post(
      `${environment.apiUrl}${path}`,
      JSON.stringify(body),
      { headers: this.defaultHeaders }
    );
  }
}