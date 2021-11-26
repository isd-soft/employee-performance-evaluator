import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsViewComponent } from './assessments-view.component';

describe('AssessmentsViewComponent', () => {
  let component: AssessmentsViewComponent;
  let fixture: ComponentFixture<AssessmentsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
