import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAssessmentWarningComponent } from './delete-assessment-warning.component';

describe('DeleteAssessmentWarningComponent', () => {
  let component: DeleteAssessmentWarningComponent;
  let fixture: ComponentFixture<DeleteAssessmentWarningComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteAssessmentWarningComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteAssessmentWarningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
