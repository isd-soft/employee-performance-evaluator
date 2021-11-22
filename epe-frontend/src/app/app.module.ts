import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from "./interceptors/token-interceptor.interceptor";

import { AppRoutingModule } from './app-routing.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule } from '@angular/material/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import {CdkAccordionModule} from '@angular/cdk/accordion';
import {MatExpansionModule} from '@angular/material/expansion';
import { MatTableModule } from '@angular/material/table'  



import { AppComponent } from './app.component';
import { LoginComponent } from './components/guest/login/login.component';
import { LogoutComponent } from './components/guest/logout/logout.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { AdminSidebarComponent } from './components/admin/admin-sidebar/admin-sidebar.component';
import { DashboardComponent } from './components/user/user-dashboard/dashboard.component';
import { UserSidebarComponent } from './components/user/user-sidebar/user-sidebar.component';
import { RegisterComponent } from './components/guest/register/register.component';
import { TeamComponent } from './components/admin/team/team.component';
import { TeamEditComponent } from './components/admin/team-edit/team-edit.component';
import { TeamNewComponent } from './components/admin/team-new/team-new.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent,
    DashboardComponent,
    UserSidebarComponent,
    AdminSidebarComponent,
    AdminDashboardComponent,
    TeamComponent,
    TeamEditComponent,
    TeamNewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    CdkAccordionModule,
    MatExpansionModule,
    MatTableModule
  ],
  exports: [
    MatButtonModule,
    MatFormFieldModule,
    MatRippleModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: 'API_URL', useValue: getApiUrl()}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function getApiUrl(): string {
  const baseHref = (document.querySelector('base') || {}).href;
  return `${baseHref}api`;
}
