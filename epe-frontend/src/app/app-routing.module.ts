import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home-component/home.component';
import { LogoutComponent } from './components/logout/logout-component/logout.component';
import { DashboardComponent } from './components/dashboard/dashboard-template/dashboard.component';

import { HomeGuard } from './guards/home/home.guard';
import { DashboardGuard } from './guards/dashboard/dashboard.guard';
import { AdminGuard } from './guards/admin/admin.guard';


const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [HomeGuard]
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [DashboardGuard]
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
