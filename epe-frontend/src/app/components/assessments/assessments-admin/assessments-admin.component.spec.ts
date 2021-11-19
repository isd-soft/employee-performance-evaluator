import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsAdminComponent } from './assessments-admin.component';

describe('AssessmentsAdminComponent', () => {
  let component: AssessmentsAdminComponent;
  let fixture: ComponentFixture<AssessmentsAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
