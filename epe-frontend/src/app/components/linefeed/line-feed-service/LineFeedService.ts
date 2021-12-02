import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LineFeedService{
  baseUrl = environment.baseUrl;
  constructor(private http: HttpClient) { }

  getAssessmentInformation(){
    return this.http.get(this.baseUrl + "api/assessments-information");
  }
}
