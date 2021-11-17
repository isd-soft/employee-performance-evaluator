import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { TokenInterceptor } from "./interceptors/token.interceptor";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LogoutComponent } from './components/logout/logout-component/logout.component';
import { SidebarComponent } from './components/sidebar/sidebar-template/sidebar.component';
import { DashboardComponent } from './components/dashboard/dashboard-template/dashboard.component';
import { HomeComponent } from './components/home/home-component/home.component';
import { TeamsComponent } from './components/teams/team-component/teams.component';
import { ProfileComponent } from './components/profile/profile-component/profile.component';
import { TeamEditComponent } from './components/teams/team-edit/team-edit.component';
import { TeamCreateComponent } from './components/teams/team-create/team-create.component';

@NgModule({
  declarations: [
    AppComponent,
    LogoutComponent,
    DashboardComponent,
    HomeComponent,
    SidebarComponent,
    TeamsComponent,
    ProfileComponent,
    TeamEditComponent,
    TeamCreateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
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
