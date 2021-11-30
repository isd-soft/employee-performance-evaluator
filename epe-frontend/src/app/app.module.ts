import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TokenInterceptor } from "./interceptors/token.interceptor";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LogoutComponent } from './components/logout/logout-component/logout.component';
import { SidebarComponent } from './components/sidebar/sidebar-template/sidebar.component';
import { DashboardComponent } from './components/dashboard/dashboard-template/dashboard.component';
import { HomeComponent } from './components/home/home-component/home.component';
import { UsersView } from './components/usersview/usersview-component/users-view.component';
import { UserComponent } from './components/user/user-component/user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatTableModule } from "@angular/material/table";
import { MatSortModule } from "@angular/material/sort";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule } from "@angular/material/dialog";
import { ProfileComponent } from './components/profile/profile-component/profile.component';
import { TeamEditComponent } from './components/teams/team-edit/team-edit.component';
import { UsersComponent } from './components/users/user-component/users.component';
import { UserCreateComponent } from './components/users/user-create/user-create.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import { MatToolbarModule } from "@angular/material/toolbar";
import { AssessmentsComponent } from './components/assessments/assessments-component/assessments.component';
import { ReplaceUnderscorePipe } from "./components/assessments/assessments-pipes/replace-underscore.pipe";
import { MatTabsModule } from "@angular/material/tabs";
import { AssessmentsTemplatesComponent } from './components/assessments-templates/assessments-templates-component/assessments-templates.component';
import { AssessmentTemplateViewComponent } from "./components/assessments-templates/assessment-template-view-component/assessment-template-view.component";
import { AssessmentTemplateEditComponent } from './components/assessments-templates/assessment-template-edit-component/assessment-template-edit.component';
import { AssessmentTemplateCreateComponent } from './components/assessments-templates/assessment-template-create-component/assessment-template-create.component';
import { DeleteConfirmationDialogComponent } from "./components/assessments-templates/assessment-template-view-component/delete-confirmation-dialog.component";
import { RoleChangeComponent } from './role-change/role-change-component/role-change.component';
import { EditComponent } from './components/edit/edit-component/edit.component';
import { PasswordComponent } from './components/password/password-component/password.component';
import { NotfoundComponent } from './components/notfound/notfound.component';
import { AssessmentComponent } from './components/assessment/assessment-component/assessment.component';
import { MatSelectModule } from "@angular/material/select";
import { ToastrModule } from "ngx-toastr";
import { MatCardModule } from "@angular/material/card";
import { MatListModule } from "@angular/material/list";
import { MatSidenavModule } from "@angular/material/sidenav";
import { AssessmentsAssignedComponent } from './components/assessments/assessments-assigned-component/assessments-assigned.component';
import { TeamsComponent } from './components/teams/team-component/teams.component';
import { LineFeedComponent } from './components/linefeed/line-feed-components/line-feed.component';
import { AssessmentsViewComponent } from './components/assessments/assessments-view/assessments-view.component';
import { AssessmentsEvaluationComponent } from './components/assessments/assessments-evaluation/assessments-evaluation.component';
import { TeamViewComponent } from './components/teams/team-view/team-view.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import { TeamDeleteComponent } from './components/teams/team-delete/team-delete.component';
import { UserDeleteComponent } from './components/usersview/user-delete/user-delete.component';
import { AssessmentsHistoryComponent } from './components/assessments/assessments-history-component/assessments-history.component';
import { AssessmentsHistoryAssignedComponent } from './components/assessments/assessments-history-assigned-component/assessments-history-assigned.component';
import {AdminBoardComponent} from "./components/admin-board/admin-board-component/admin-board.component";
import {MatBadgeModule} from '@angular/material/badge';
import { AdminStatsComponent } from './components/admin-stats/admin-stats-component/admin-stats.component';
import {NgApexchartsModule} from "ng-apexcharts";
import { CreateUserComponent } from './create-user/create-component/create-user.component';
import {MatMenuModule} from "@angular/material/menu";
import { CancelAssessmentComponent } from './components/cancel-assessment/cancel-assessment-component/cancel-assessment.component';

@NgModule({
  declarations: [
    AppComponent,
    LogoutComponent,
    DashboardComponent,
    HomeComponent,
    SidebarComponent,
    UsersView,
    UserComponent,
    ProfileComponent,
    TeamEditComponent,
    UsersComponent,
    UserEditComponent,
    UserCreateComponent,
    AssessmentsComponent,
    ReplaceUnderscorePipe,
    AssessmentsTemplatesComponent,
    AssessmentTemplateViewComponent,
    AssessmentTemplateEditComponent,
    AssessmentTemplateCreateComponent,
    DeleteConfirmationDialogComponent,
    SidebarComponent,
    TeamsComponent,
    EditComponent,
    PasswordComponent,
    NotfoundComponent,
    AssessmentComponent,
    PasswordComponent,
    RoleChangeComponent,
    AssessmentsAssignedComponent,
    TeamViewComponent,
    TeamDeleteComponent,
    AssessmentsViewComponent,
    AssessmentsEvaluationComponent,
    UserDeleteComponent,
    AssessmentsHistoryComponent,
    AssessmentsHistoryAssignedComponent,
    TeamViewComponent,
    AssessmentsAssignedComponent,
    LineFeedComponent,
    AdminStatsComponent,
    AdminBoardComponent,
    CreateUserComponent,
    CancelAssessmentComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
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
        MatToolbarModule,
        MatTabsModule,
        MatSelectModule,
        ToastrModule.forRoot(),
        MatCardModule,
        MatListModule,
        MatSidenavModule,
        MatDatepickerModule,
        MatMenuModule,
        MatBadgeModule,
        NgApexchartsModule,
        // MatPaginator,
        // MatSort,
        // MatTableDataSource,
        // MatFormFieldModule
    ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: 'API_URL', useValue: getApiUrl() }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function getApiUrl(): string {
  const baseHref = (document.querySelector('base') || {}).href;
  return `${baseHref}api`;
}
