import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";

@Injectable({
  providedIn: 'root'
})
export class AssessmentsService {

  assessmentsUrl: string = 'api-server/api/users';

  constructor(private http: HttpClient) { }

  getAllAssessmentsByUserAndStatus(userId : string, status : string) {
    return this.http.get(this.assessmentsUrl + '/' + userId + '/assessments?status=' + status)
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
