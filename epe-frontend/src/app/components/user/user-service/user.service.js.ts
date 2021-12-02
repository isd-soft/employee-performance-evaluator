import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import {User} from '../user-model/User'
import {environment} from "../../../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl = environment.baseUrl;
  url: string = this.baseUrl + 'api/users';


  constructor(private http: HttpClient) {

  }

  getUser(userId: string) {
   return this.http.get(`${this.url}/${userId}`);
  }

  getAssessment(userId: string){
    // console.log(this.http.get(`${this.url}/${userId}/assessments`))
    return this.http.get(`${this.url}/${userId}/assessments`);
  }


  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
