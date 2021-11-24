import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsAssignedComponent } from './assessments-assigned.component';

describe('AssessmentsAssignedComponent', () => {
  let component: AssessmentsAssignedComponent;
  let fixture: ComponentFixture<AssessmentsAssignedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsAssignedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsAssignedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
