import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AssessmentsTemplatesService {

  assessmentsTemplatesUrl: string = 'api-server/api/assessments-templates';

  constructor(private http: HttpClient) { }

  getAllAssessmentsTemplates() {
    return this.http.get(this.assessmentsTemplatesUrl)
                    .pipe(catchError(this.errorHandler));
  }

  getAssessmentTemplate(id: string) {
    return this.http.get(this.assessmentsTemplatesUrl + '/' + id);
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }


}
