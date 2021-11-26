import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmartgoalDescriptionComponent } from './smart-goal-description.component';

describe('SmartgoalDescriptionComponent', () => {
  let component: SmartgoalDescriptionComponent;
  let fixture: ComponentFixture<SmartgoalDescriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SmartgoalDescriptionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SmartgoalDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
