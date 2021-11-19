import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { TokenInterceptor } from "./interceptors/token.interceptor";

// import { TableOverviewExample } from './table-overview-example/table-overview-example.component';
// import {MatPaginator} from '@angular/material/paginator';
// import {MatSort} from '@angular/material/sort';
// import {MatTableDataSource} from '@angular/material/table';
// import {MatFormFieldModule} from '@angular/material/form-field';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LogoutComponent } from './components/logout/logout-component/logout.component';
import { SidebarComponent } from './components/sidebar/sidebar-template/sidebar.component';
import { DashboardComponent } from './components/dashboard/dashboard-template/dashboard.component';
import { HomeComponent } from './components/home/home-component/home.component';
import { TeamsComponent } from './components/teams/team-component/teams.component';
import { UsersView } from './components/usersview/usersview-component/users-view.component';
import { UserComponent } from './components/user/user-component/user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatDialogModule} from "@angular/material/dialog";
import { ProfileComponent } from './components/profile/profile-component/profile.component';
import { TeamEditComponent } from './components/teams/team-edit/team-edit.component';
import { TeamCreateComponent } from './components/teams/team-create/team-create.component';
import { UsersComponent } from './components/users/user-component/users.component';
import { UserCreateComponent } from './components/users/user-create/user-create.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import { AssessmentsUserComponent } from './components/assessments/assessments-user/assessments-user.component';
import { AssessmentsAdminComponent } from './components/assessments/assessments-admin/assessments-admin.component';
import {ReplaceUnderscorePipe} from "./components/assessments/assessments-pipes/replace-underscore.pipe";
import { AssessmentsHistoryUserComponent } from './components/assessments/assessments-history-user/assessments-history-user.component';
import { EditComponent } from './edit/edit-component/edit.component';
@NgModule({
  declarations: [
    AppComponent,
    LogoutComponent,
    DashboardComponent,
    HomeComponent,
    SidebarComponent,
    TeamsComponent,
    UsersView,
    UserComponent,
    TeamsComponent,
    ProfileComponent,
    TeamEditComponent,
    TeamCreateComponent,
    UsersComponent,
    UserEditComponent,
    UserCreateComponent,
    AssessmentsUserComponent,
    AssessmentsAdminComponent,
    ReplaceUnderscorePipe,
    AssessmentsHistoryUserComponent,
    SidebarComponent,
    TeamsComponent,
    EditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    FormsModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    // MatPaginator,
    // MatSort,
    // MatTableDataSource,
    // MatFormFieldModule
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
