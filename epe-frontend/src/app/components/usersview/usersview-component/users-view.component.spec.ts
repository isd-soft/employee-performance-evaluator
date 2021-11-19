import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersView } from './users-view.component';

describe('OverviewComponent', () => {
  let component: UsersView;
  let fixture: ComponentFixture<UsersView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersView ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersView);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
