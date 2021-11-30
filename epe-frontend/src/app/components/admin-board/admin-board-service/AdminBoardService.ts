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

  exportAssessmentToPdf(assessment: AssessmentView | undefined) {
    return this.http.get('api-server/api/export/' + assessment?.id + '/assessment',{responseType: 'arraybuffer'}).subscribe(pdf => {
      const blob = new Blob([pdf],{type: 'application/pdf'});

      let datePipe = new DatePipe('en-US');
      // @ts-ignore
      let currentDate = datePipe.transform(Date.now(), 'dd-MM-yyyy') as string;
      const fileName = assessment?.evaluatedUserFullName + '_' + currentDate +'.pdf';
      saveAs(blob,fileName);
    });
  }

}
