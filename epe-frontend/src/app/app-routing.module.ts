import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home-component/home.component';
import { LogoutComponent } from './components/logout/logout-component/logout.component';
import { DashboardComponent } from './components/dashboard/dashboard-template/dashboard.component';

import { TeamsComponent } from './components/teams/team-component/teams.component';
import { TeamEditComponent } from './components/teams/team-edit/team-edit.component';
import { TeamCreateComponent } from './components/teams/team-create/team-create.component';

import { ProfileComponent } from './components/profile/profile-component/profile.component';

import { UsersComponent } from './components/users/user-component/users.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import { UserCreateComponent } from './components/users/user-create/user-create.component';

import { HomeGuard } from './guards/home/home.guard';
import { DashboardGuard } from './guards/dashboard/dashboard.guard';
import {UsersView} from "./components/usersview/usersview-component/users-view.component";
import {UserComponent} from "./components/user/user-component/user.component";
import {AdminGuard} from "./guards/admin/admin.guard";
import {AssessmentsUserComponent} from "./components/assessments/assessments-user/assessments-user.component";
import {AssessmentsHistoryUserComponent} from "./components/assessments/assessments-history-user/assessments-history-user.component";

import { EditComponent } from './components/edit/edit-component/edit.component';
import {PasswordComponent} from "./components/password/password-component/password.component";
import {NotfoundComponent} from "./components/notfound/notfound.component";


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
    canActivate: [AdminGuard]
  },
  {
    path: 'team-edit',
    component: TeamEditComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'team-create',
    component: TeamCreateComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'users',
    component: UsersComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'user-edit',
    component: UserEditComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'user-create',
    component: UserCreateComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'my-profile',
    component: ProfileComponent,
    canActivate: [DashboardGuard]
  },
  {
    path: 'user',
    component: UserComponent,
  },
  {
    path: "edit",
    component: EditComponent
  },
  {
    path: 'edit/password',
    component: PasswordComponent
  },
  {
    path: 'usersview',
    component: UsersView,
  },
  {
    path: 'assessments',
    component: AssessmentsUserComponent,
    canActivate: [DashboardGuard]
  },
  {
    path: 'assessments-history',
    component: AssessmentsHistoryUserComponent,
    canActivate: [DashboardGuard]
  },
  {
    path: '**',
    component: NotfoundComponent
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
