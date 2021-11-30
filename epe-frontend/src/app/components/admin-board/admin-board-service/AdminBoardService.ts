import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";

@Injectable({
  providedIn: 'root'
})
export class AdminBoardService {

  assessmentsUrl: string = 'api-server/api/';


  constructor(private http: HttpClient,
              private notificationService: ToastrService,
              private jwtService: JwtService) {}

  getAllAssessments(){
    return this.http.get("api-server/api/assessments");
  }

  deleteAssessment(assessmentId: string){
    this.http.request('delete', "api-server/api/assessments/" + assessmentId ,
      {body: {"startedById": this.jwtService.getJwtUser().id}}).subscribe();

  }

}
