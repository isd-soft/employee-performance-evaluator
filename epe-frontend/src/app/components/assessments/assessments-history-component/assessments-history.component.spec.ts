import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsHistoryComponent } from './assessments-history.component';

describe('AssessmentsHistoryAssignedComponent', () => {
  let component: AssessmentsHistoryComponent;
  let fixture: ComponentFixture<AssessmentsHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
