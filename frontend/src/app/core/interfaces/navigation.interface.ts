export interface NavigationMenuItem {
  label: string;
  icon: string;
  route: string;
  permission: string | string[];
}

export const navigationMenu: NavigationMenuItem[] = [
  {
    label: 'Dashboard',
    icon: 'dashboard',
    route: '/dashboard',
    permission: ['APPROVE_APPLICATION','REVIEW_APPLICATION']
  },
  {
    label: 'Applications',
    icon: 'folder',
    route: '/applications',
    permission: ['VIEW_ALL', 'CREATE_APPLICATION', 'VIEW_OWN_APPLICATION','APPROVE_APPLICATION','VIEW_ASSIGNED_APPLICATIONS','REVIEW_APPLICATION']
  },
  {
    label: 'Create User',
    icon: 'person_add',
    route: '/auth/create/user/new',
    permission: 'MANAGE_USERS'
  }
];