import { Component } from '@angular/core';
import { navigationMenu } from '../../core/interfaces/navigation.interface'
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {

  constructor(public auth: AuthService) { }


  menuItems = navigationMenu

  hasMenuPermission(permission: string | string[]): boolean {
  if (Array.isArray(permission)) {
    return permission.some(p => this.auth.hasPermission(p));
  }

  return this.auth.hasPermission(permission);
}

}
