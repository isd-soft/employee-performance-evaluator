import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdminStatsService{

  actuatorUrl: string = 'http://localhost:8075/api/actuator/';
  constructor(private httpClient: HttpClient) { }

  getResponseStatusesCount200(){ return this.httpClient.get(this.actuatorUrl + 'metrics/http.server.requests?tag=status:200');}
  getResponseStatusesCount404(){ return this.httpClient.get(this.actuatorUrl + 'metrics/http.server.requests?tag=status:404');}
  getResponseStatusesCount400(){ return this.httpClient.get(this.actuatorUrl + 'metrics/http.server.requests?tag=status:400');}
  getResponseStatusesCount500(){ return this.httpClient.get(this.actuatorUrl + 'metrics/http.server.requests?tag=status:500');}
  getHealth(){ return this.httpClient.get(this.actuatorUrl + 'health');}
  getProcessorsCount(){ return this.httpClient.get(this.actuatorUrl + 'metrics/system.cpu.count');}
  getProcessUptime(){ return this.httpClient.get(this.actuatorUrl + 'metrics/process.uptime');}
}
