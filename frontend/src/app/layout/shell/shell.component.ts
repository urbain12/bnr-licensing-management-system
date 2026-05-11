import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shell',
  templateUrl: './shell.component.html',
  styleUrls: ['./shell.component.scss']
})
export class ShellComponent {

  constructor (
    private route: Router
  ) {}

  logout (): void {
    localStorage.clear();
    this.route.navigateByUrl('/auth/login');
  }

}
