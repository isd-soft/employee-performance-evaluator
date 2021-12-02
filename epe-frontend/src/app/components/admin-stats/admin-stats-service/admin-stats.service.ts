import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminStatsService{
  baseUrl = environment.baseUrl;

  actuatorUrl: string = this.baseUrl + 'api/actuator/';
  assessmentsUrl: string = this.baseUrl + 'api/assessments';
  usersUrl: string = this.baseUrl + 'api/users';
  teamsUrl: string = this.baseUrl + 'api/teams';
  constructor(private httpClient: HttpClient) { }

  getResponseStatusesCount200(){ return this.httpClient.get(this.actuatorUrl + 'metrics/http.server.requests?tag=status:200');}
  getResponseStatusesCount404(){ return this.httpClient.get(this.actuatorUrl + 'metrics/http.server.requests?tag=status:404');}
  getResponseStatusesCount400(){ return this.httpClient.get(this.actuatorUrl + 'metrics/http.server.requests?tag=status:400');}
  getResponseStatusesCount500(){ return this.httpClient.get(this.actuatorUrl + 'metrics/http.server.requests?tag=status:500');}
  getHealth(){ return this.httpClient.get(this.actuatorUrl + 'health');}
  getProcessorsCount(){ return this.httpClient.get(this.actuatorUrl + 'metrics/system.cpu.count');}
  getProcessUptime(){ return this.httpClient.get(this.actuatorUrl + 'metrics/process.uptime');}
  getHttpTraces(){ return this.httpClient.get(this.actuatorUrl + 'httptrace');}
  getAssessmentsCount(){ return this.httpClient.get(this.assessmentsUrl + '?count=all');}
  getNewAssessmentsThisYearCount(){ return this.httpClient.get(this.assessmentsUrl + '?count=current-year');}
  getUsersCount(){ return this.httpClient.get(this.usersUrl + '?count=all');}
  getNewUsersThisYearCount(){ return this.httpClient.get(this.usersUrl + '?count=current-year');}
  getBuddiesCount(){ return this.httpClient.get(this.usersUrl + '?count=buddies');}
  getTeamLeadersCount(){ return this.httpClient.get(this.teamsUrl + '?count=leaders');}
}
