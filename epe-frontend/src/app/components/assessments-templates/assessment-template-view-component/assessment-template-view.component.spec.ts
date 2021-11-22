import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentTemplateViewComponent } from './assessment-template-view.component';

describe('AssessmentTemplateViewComponent', () => {
  let component: AssessmentTemplateViewComponent;
  let fixture: ComponentFixture<AssessmentTemplateViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssessmentTemplateViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentTemplateViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
