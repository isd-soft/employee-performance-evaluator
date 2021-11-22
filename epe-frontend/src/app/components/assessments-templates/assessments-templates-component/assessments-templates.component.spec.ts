import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentsTemplatesComponent } from './assessments-templates.component';

describe('AssessmentsTemplatesComponent', () => {
  let component: AssessmentsTemplatesComponent;
  let fixture: ComponentFixture<AssessmentsTemplatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentsTemplatesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentsTemplatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
