import { ShortUser } from './../../teams/teams-model/short-user.interface';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError, delay, share } from 'rxjs/operators';
import {User} from "../userview-models/User";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";

// @ts-ignore
import {saveAs} from 'file-saver/dist/FileSaver';
import {NewUser} from "../userview-models/NewUser";
import {DatePipe} from "@angular/common";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class UserviewsServices {

  baseUrl = environment.baseUrl;
  url: string = this.baseUrl + 'api/users';
  url2: string = this.baseUrl + 'api/teams';
  url3: string = this.baseUrl + 'api/export';
  url4: string = this.baseUrl + 'api/export/pdf/generate';
  url5: string = this.baseUrl + 'api/export/pdf/users';
  url6: string = this.baseUrl + 'api/export/excel/users';

  jwtUser?: JwtUser;
  role? : string;
  id? : string;

  constructor(private http: HttpClient, private jwtService: JwtService) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser) {
      this.role = this.jwtUser.role;
      this.id = this.jwtUser.id;
    }
  }

  getUserList() {
    return this.http.get(this.url);
  }

  getAssignedUsers(){
    return this.http.get(this.baseUrl + "api/users/" + this.jwtUser?.id + "/assignedUsers");
  }

  deleteUser(userId : string | undefined) {
    //return this.http.delete(this.url + '/' + userId)
    return this.http.delete(this.url + '/' + userId).subscribe();
  }

  getTeams() {
    return this.http.get(this.url2);
  }

  getTeamMembers() {
    return this.http.get(this.url2 + '/' + this.id + '/members')
  }

  getTeamMembersArray(): Observable<ShortUser[]> {
    return this.http.get<ShortUser[]>(this.url2 + '/' + this.id + '/members').pipe(share(), delay(1000));
  }

  getTeamLeader() {
    return this.http.get(this.url2 + '/' + this.id + '/leader')
  }

  getAssessmentTemplates() {
    return this.http.get(this.baseUrl + "api/assessments-templates");
  }

  exportToPdf(user : NewUser | undefined) {
    //return this.http.get(this.url3 + '/' + userId + '/pdf/generate');
    return this.http.get(this.url5 + '/' + user?.id,{responseType: 'arraybuffer'}).subscribe(pdf => {
      const blob = new Blob([pdf],{type: 'application/pdf'});

      let datePipe = new DatePipe('en-US');
      // @ts-ignore
      let currentDate = datePipe.transform(Date.now(), 'dd-MM-yyyy') as string;
      const fileName = user?.firstname + '_' + user?.lastname + '_' + currentDate +'.pdf';
      saveAs(blob,fileName);
    });
  }

  exportToExcel(user : NewUser | undefined) {
    return this.http.get(this.url3 + '/' + user?.id + '/excel',{responseType: 'arraybuffer'}).subscribe(xlsx => {
      const blob = new Blob([xlsx], {type: 'application/octet-stream'});
      let datePipe = new DatePipe('en-US');
      // @ts-ignore
      let currentDate = datePipe.transform(Date.now(), 'dd-MM-yyyy') as string;
      const fileName = user?.firstname + '_' + user?.lastname + '_' + currentDate +'.xlsx';
      saveAs(blob,fileName);
    });

  }

  exportAllToPdf() {
    return this.http.get(this.url5,{responseType: 'arraybuffer'}).subscribe(pdf => {
      const blob = new Blob([pdf],{type: 'application/pdf'});

      let datePipe = new DatePipe('en-US');
      // @ts-ignore
      let currentDate = datePipe.transform(Date.now(), 'dd-MM-yyyy') as string;
      const fileName = 'users_' + currentDate +'.pdf';
      saveAs(blob,fileName);
    });
  }

  exportAllToExcel() {
    return this.http.get(this.url6,{responseType: 'arraybuffer'}).subscribe(xlsx => {
      const blob = new Blob([xlsx], {type: 'application/octet-stream'});
      let datePipe = new DatePipe('en-US');
      // @ts-ignore
      let currentDate = datePipe.transform(Date.now(), 'dd-MM-yyyy') as string;
      const fileName = 'users_' + currentDate +'.xlsx';
      saveAs(blob,fileName);
    });
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
