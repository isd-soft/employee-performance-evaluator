import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import {User} from "../userview-models/User";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";

// @ts-ignore
import {saveAs} from 'file-saver/dist/FileSaver';
import {NewUser} from "../userview-models/NewUser";
import {DatePipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})

export class UserviewsServices {

  url: string = 'api-server/api/users';
  url2: string = 'api-server/api/teams';
  url3: string = 'api-server/api/export';
  url4: string = 'api-server/api/export/pdf/generate';
  url5: string = 'api-server/api/export/pdf/users';

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
    return this.http.get("api-server/api/users/" + this.jwtUser?.id + "/assignedUsers");
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

  getTeamLeader() {
    return this.http.get(this.url2 + '/' + this.id + '/leader')
  }

  getAssessmentTemplates() {
    return this.http.get("api-server/api/assessments-templates");
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

  }

  exportAllToExcel() {

  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
