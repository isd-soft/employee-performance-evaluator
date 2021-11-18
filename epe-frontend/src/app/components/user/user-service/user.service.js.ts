import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import {User} from '../user-model/User'


@Injectable({
  providedIn: 'root'
})
export class UserService {

  userURL: string = 'api-server/api/users/c16d935b-2588-4531-9734-3d88d1a96a2c';
  AssessmentUrl: string = 'api-server/api/users/c16d935b-2588-4531-9734-3d88d1a96a2c/assessments';
  userID?: string;


  constructor(private http: HttpClient) {
    this.userURL = this.userURL ;

  }

  getUser() {
   return this.http.get(this.userURL);
  }

  getAssessment(){
    return this.http.get((this.AssessmentUrl));
  }


  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
