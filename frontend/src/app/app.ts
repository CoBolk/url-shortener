import { Component, Inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UrlService } from './services/url-shortener.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('URL_frontend');
}
