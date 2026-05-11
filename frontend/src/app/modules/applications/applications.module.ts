import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared.module';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';


import { ApplicationsRoutingModule } from './applications-routing.module';
import { ApplicationsListComponent } from './applications-list/applications-list.component';
import { ApplicationsDetailComponent } from './applications-detail/applications-detail.component';
import { ApplicationsCreateComponent } from './applications-create/applications-create.component';
import { DocumentUploadComponent } from './document-upload/document-upload.component';


@NgModule({
  declarations: [
    ApplicationsListComponent,
    ApplicationsDetailComponent,
    ApplicationsCreateComponent,
    DocumentUploadComponent
  ],
  imports: [
    CommonModule,
    ApplicationsRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ApplicationsModule { }
