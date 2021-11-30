import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelAssessmentComponent } from './cancel-assessment.component';

describe('CancelAssessmentComponent', () => {
  let component: CancelAssessmentComponent;
  let fixture: ComponentFixture<CancelAssessmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelAssessmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CancelAssessmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
