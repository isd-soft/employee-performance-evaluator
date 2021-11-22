import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentTemplateCreateComponent } from './assessment-template-create.component';

describe('AssessmentTemplateCreateComponent', () => {
  let component: AssessmentTemplateCreateComponent;
  let fixture: ComponentFixture<AssessmentTemplateCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentTemplateCreateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentTemplateCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
