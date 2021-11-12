import { TestBed } from '@angular/core/testing';

import { JwtService } from './jwt.service';

describe('JwtDecoderService', () => {
  let service: JwtDecoderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
