import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LogoutComponent } from './logout/logout.component';
import { TestComponent } from './test/test.component';
import { LoginComponent } from './login/login-template/login.component';
import { RegisterComponent } from './register/register-template/register.component';

import { AuthGuard } from './guards/auth-guard/auth.guard';
import { HomeGuard } from './guards/home/home.guard';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [HomeGuard]
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [HomeGuard]
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'test',
    component: TestComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
