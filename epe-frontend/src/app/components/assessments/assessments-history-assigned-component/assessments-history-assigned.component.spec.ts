import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsHistoryAssignedComponent } from './assessments-history-assigned.component';

describe('AssessmentsHistoryAssignedComponent', () => {
  let component: AssessmentsHistoryAssignedComponent;
  let fixture: ComponentFixture<AssessmentsHistoryAssignedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsHistoryAssignedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsHistoryAssignedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
