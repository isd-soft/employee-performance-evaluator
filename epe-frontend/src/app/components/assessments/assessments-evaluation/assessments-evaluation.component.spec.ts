import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsEvaluationComponent } from './assessments-evaluation.component';

describe('AssessmentsEvaluationComponent', () => {
  let component: AssessmentsEvaluationComponent;
  let fixture: ComponentFixture<AssessmentsEvaluationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsEvaluationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsEvaluationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
