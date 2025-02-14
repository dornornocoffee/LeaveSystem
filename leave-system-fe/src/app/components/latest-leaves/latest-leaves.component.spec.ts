import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LatestLeavesComponent } from './latest-leaves.component';

describe('LatestLeavesComponent', () => {
  let component: LatestLeavesComponent;
  let fixture: ComponentFixture<LatestLeavesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LatestLeavesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LatestLeavesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
