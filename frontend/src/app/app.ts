import { Component, Inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UrlService } from './services/url.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('URL_frontend');

  constructor(private urlService: UrlService) {
    this.urlService.testCors().subscribe({
      next: (data: any) => console.log('CORS working !', data),
      error: (err: any) => console.error('CORS not working', err),
    });
  }
}
