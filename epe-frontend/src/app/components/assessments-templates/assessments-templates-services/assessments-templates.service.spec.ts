import { TestBed } from '@angular/core/testing';

import { AssessmentsTemplatesService } from './assessments-templates.service';

describe('AssessmentsTemplatesService', () => {
  let service: AssessmentsTemplatesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssessmentsTemplatesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
