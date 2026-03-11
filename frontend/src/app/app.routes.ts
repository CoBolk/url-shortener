import { Routes } from '@angular/router';
import { Home } from './home/home/home';

export const routes: Routes = [
  {
    path: '**',
    component: Home,
  },
];
