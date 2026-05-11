import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { PermissionGuard } from '../../core/guards/permission.guard';

import { ApplicationsListComponent } from './applications-list/applications-list.component';
import { ApplicationsCreateComponent } from './applications-create/applications-create.component';
import { ApplicationsDetailComponent } from './applications-detail/applications-detail.component';


const routes: Routes = [
  {
    path: '',
    component: ApplicationsListComponent,
    canActivate: [AuthGuard, PermissionGuard],
    data: {
      permissions: ['VIEW_ALL', 'CREATE_APPLICATION', 'VIEW_OWN_APPLICATION','APPROVE_APPLICATION','VIEW_ASSIGNED_APPLICATIONS','REVIEW_APPLICATION']
    }
  },
  {
    path: 'new',
    component: ApplicationsCreateComponent,
    canActivate: [AuthGuard, PermissionGuard],
    data: {
      permissions: ['CREATE_APPLICATION']
    }
  },
  {
    path: ':id',
    component: ApplicationsDetailComponent,
    canActivate: [AuthGuard, PermissionGuard],
    data: {
      permissions: ['VIEW_ALL', 'CREATE_APPLICATION', 'VIEW_OWN_APPLICATION','APPROVE_APPLICATION','VIEW_ASSIGNED_APPLICATIONS','REVIEW_APPLICATION']
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationsRoutingModule { }