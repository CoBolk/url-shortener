import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ShortenResponse } from '../models/url.model';

@Injectable({
  providedIn: 'root',
})
export class UrlService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api';
  readonly baseUrl = 'http://localhost:8080';

  // testCors() {
  //   return this.http.post(`${this.apiUrl}/shorten`, this.body);
  // }

  shorten(url: string) {
    return this.http.post<ShortenResponse>(`${this.apiUrl}/shorten`, { url });
  }
}
