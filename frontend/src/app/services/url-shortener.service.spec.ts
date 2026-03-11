import { TestBed } from '@angular/core/testing';

import { UrlService } from './url-shortener.service';

describe('UrlServiceTs', () => {
  let service: UrlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UrlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
