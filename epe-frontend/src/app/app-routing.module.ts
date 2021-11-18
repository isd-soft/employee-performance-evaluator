import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home-component/home.component';
import { LogoutComponent } from './components/logout/logout-component/logout.component';
import { DashboardComponent } from './components/dashboard/dashboard-template/dashboard.component';
import { TeamsComponent } from './components/teams/team-component/teams.component';

import { HomeGuard } from './guards/home/home.guard';
import { DashboardGuard } from './guards/dashboard/dashboard.guard';
import {UsersView} from "./components/usersview/usersview-component/users-view.component";
import {UserComponent} from "./components/user/user-component/user.component";


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
    path: 'teams',
    component: TeamsComponent,
    canActivate: [DashboardGuard]
  },
  {
    path: 'user',
    component: UserComponent,
  },
  {
    path: 'usersview',
    component: UsersView,
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
