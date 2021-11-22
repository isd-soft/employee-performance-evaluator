import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentTemplateEditComponent } from './assessment-template-edit.component';

describe('AssessmentTemplateEditComponent', () => {
  let component: AssessmentTemplateEditComponent;
  let fixture: ComponentFixture<AssessmentTemplateEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentTemplateEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentTemplateEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
