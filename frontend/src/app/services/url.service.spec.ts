import { TestBed } from '@angular/core/testing';

import { UrlServiceTs } from './url.service.js';

describe('UrlServiceTs', () => {
  let service: UrlServiceTs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UrlServiceTs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
