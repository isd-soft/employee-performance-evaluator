import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";

@Injectable({
  providedIn: 'root'
})
export class LineFeedService{
  constructor(private http: HttpClient) { }

  getAssessmentInformation(){
    return this.http.get("api-server/api/assessments-information");
  }
}
