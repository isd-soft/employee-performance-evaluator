import { AdminGuard } from './guards/admin/admin.guard';
import { UserGuard } from './guards/user/user.guard';
import { GuestGuard } from './guards/guest/guest.guard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './components/guest/login/login.component';
import { LogoutComponent } from './components/guest/logout/logout.component';
import { RegisterComponent } from './components/guest/register/register.component';
import { DashboardComponent } from './components/user/user-dashboard/dashboard.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { TeamComponent } from './components/admin/team/team.component';
import { TeamNewComponent } from './components/admin/team-new/team-new.component';
import { TeamEditComponent } from './components/admin/team-edit/team-edit.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'teams',
    component: TeamComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'team-new',
    component: TeamNewComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'team-edit/:id',
    component: TeamEditComponent,
    canActivate: [AdminGuard]
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
