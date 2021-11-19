import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsHistoryUserComponent } from './assessments-history-user.component';

describe('AssessmentsHistoryUserComponent', () => {
  let component: AssessmentsHistoryUserComponent;
  let fixture: ComponentFixture<AssessmentsHistoryUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsHistoryUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsHistoryUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
