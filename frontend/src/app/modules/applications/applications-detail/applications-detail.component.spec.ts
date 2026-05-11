import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationsDetailComponent } from './applications-detail.component';

describe('ApplicationsDetailComponent', () => {
  let component: ApplicationsDetailComponent;
  let fixture: ComponentFixture<ApplicationsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApplicationsDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplicationsDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
