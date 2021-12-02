import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {AssessmentView} from "../../assessments/assessments-models/assessment-view.interface";
import {DatePipe} from "@angular/common";

// @ts-ignore
import {saveAs} from 'file-saver/dist/FileSaver';
import {environment} from "../../../../environments/environment";
@Injectable({
  providedIn: 'root'
})
export class AdminBoardService {

  baseUrl = environment.baseUrl;
  assessmentsUrl: string = this.baseUrl + 'api/';


  constructor(private http: HttpClient,
              private notificationService: ToastrService,
              private jwtService: JwtService) {}

  getAllAssessments(){
    return this.http.get(this.baseUrl + "assessments");
  }

  deleteAssessment(assessmentId: string){
    this.http.request('delete', this.baseUrl + "api/assessments/" + assessmentId ,
      {body: {"startedById": this.jwtService.getJwtUser().id}}).subscribe();

  }

  exportAssessmentToPdf(assessment: AssessmentView | undefined) {
    return this.http.get(this.baseUrl + 'api/export/' + assessment?.id + '/assessment/pdf',{responseType: 'arraybuffer'}).subscribe(pdf => {
      const blob = new Blob([pdf],{type: 'application/pdf'});

      let datePipe = new DatePipe('en-US');
      // @ts-ignore
      let currentDate = datePipe.transform(Date.now(), 'dd-MM-yyyy') as string;
      const fileName = assessment?.evaluatedUserFullName + '_' + currentDate +'.pdf';
      saveAs(blob,fileName);
    });
  }

  exportAssessmentToExcel(assessment: AssessmentView | undefined) {
    return this.http.get(this.baseUrl + 'api/export/' + assessment?.id + '/assessment/excel',{responseType: 'arraybuffer'}).subscribe(xlsx => {
      const blob = new Blob([xlsx], {type: 'application/octet-stream'});
      let datePipe = new DatePipe('en-US');
      // @ts-ignore
      let currentDate = datePipe.transform(Date.now(), 'dd-MM-yyyy') as string;
      const fileName = assessment?.evaluatedUserFullName + '_' + currentDate +'.xlsx';
      saveAs(blob,fileName);
    });
  }


}
