import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LineFeedComponent } from './line-feed.component';

describe('LineFeedComponent', () => {
  let component: LineFeedComponent;
  let fixture: ComponentFixture<LineFeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LineFeedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LineFeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
