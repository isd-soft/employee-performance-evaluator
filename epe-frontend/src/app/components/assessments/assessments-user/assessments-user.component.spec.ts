import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsUserComponent } from './assessments-user.component';

describe('AssessmentsUserComponent', () => {
  let component: AssessmentsUserComponent;
  let fixture: ComponentFixture<AssessmentsUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
