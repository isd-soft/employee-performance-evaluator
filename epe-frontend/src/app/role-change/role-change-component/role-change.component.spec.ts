import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleChangeComponent } from './role-change.component';

describe('RoleChangeComponent', () => {
  let component: RoleChangeComponent;
  let fixture: ComponentFixture<RoleChangeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleChangeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleChangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
