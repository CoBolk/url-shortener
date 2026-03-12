import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import {
  UrlShortenRequest,
  UrlShortenResponse,
} from '../models/url-shorten.model';

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

  shorten(mainUrl: string) {
    return this.http.post<UrlShortenResponse>(`${this.apiUrl}/shorten`, {
      mainUrl,
    });
  }
}
