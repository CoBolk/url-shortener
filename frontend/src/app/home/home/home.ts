import { Component, DestroyRef, inject, signal } from '@angular/core';
import { UrlService } from '../../services/url-shortener.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

interface ShortenResult {
  shortUrl: string;
  originalUrl: string;
}

@Component({
  selector: 'app-home',
  imports: [ReactiveFormsModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  private readonly urlService = inject(UrlService);
  private readonly fb = inject(FormBuilder);
  private readonly destroyRef = inject(DestroyRef);

  readonly result = signal<ShortenResult | null>(null);
  readonly isLoading = signal(false);
  readonly errorMessage = signal<string | null>(null);
  readonly copied = signal(false);

  readonly form = this.fb.group({
    url: ['', [Validators.required, Validators.pattern(/^https?:\/\/.+./)]],
  });

  get isFieldInvalid() {
    const ctrl = this.form.controls.url;
    return ctrl.invalid && ctrl.touched;
  }

  onSubmit() {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;

    this.isLoading.set(true);

    const originalUrl = this.form.controls.url.value!;

    this.urlService
      .shorten(originalUrl)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: ({ shortCode }) => {
          (this.result.set({
            shortUrl: `${this.urlService.baseUrl}/${shortCode}`,
            originalUrl,
          }),
            this.isLoading.set(false));
        },
        error: () => {
          (this.errorMessage.set('An error has occured'),
            this.isLoading.set(false));
        },
      });
  }

  async copyToClipboard(url: string) {
    await navigator.clipboard.writeText(url);
    this.copied.set(true);
    setTimeout(() => this.copied.set(false), 2000);
  }
}
