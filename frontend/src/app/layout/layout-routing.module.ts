import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShellComponent } from './shell/shell.component';
import { AuthGuard } from '../core/guards/auth.guard';
import { PermissionGuard } from '../core/guards/permission.guard';
import { CreateUserComponent } from '../modules/auth/create-user/create-user.component';

const routes: Routes = [
  {
    path: '',
    component: ShellComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        loadChildren: () =>
          import('../modules/dashboard/dashboard.module')
            .then(m => m.DashboardModule)
      },
      {
        path: 'applications',
        loadChildren: () =>
          import('../modules/applications/applications.module')
            .then(m => m.ApplicationsModule)
      },
      {
        path: 'users/new',
        component: CreateUserComponent,
        canActivate: [PermissionGuard],
        data: {
          permissions: ['MANAGE_USERS']
        }
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule { }
