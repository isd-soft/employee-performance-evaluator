import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdminStatsService{

  actuatorUrl: string = 'http://localhost:8075/api/actuator/';
  assessmentsUrl: string = 'http://localhost:8075/api/assessments';
  usersUrl: string = 'http://localhost:8075/api/users';
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
}
