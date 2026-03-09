import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UrlService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  private body = { url: 'https://google.com' };

  testCors() {
    return this.http.post(`${this.apiUrl}/shorten`, this.body);
  }
}
