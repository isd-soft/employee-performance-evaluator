import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {TokenInterceptor} from "./interceptors/token.interceptor";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register-template/register.component';
import { LoginComponent } from './login/login-template/login.component';
import { TestComponent } from './test/test.component';
import { LogoutComponent } from './logout/logout.component';
import { DashboardComponent } from './dashboard/dashboard-template/dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    TestComponent,
    LogoutComponent,
    DashboardComponent
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
