import { JobItem } from './../home-models/job-item.interface';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { RegisterRequest } from '../home-models/register-request.interface';
import { LoginRequest } from '../home-models/login-request.interface';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import {environment} from "../../../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class HomeService {

  baseUrl = environment.baseUrl;
  url: string = this.baseUrl + 'api/auth/';

  constructor(private http: HttpClient) { }

  login(user: LoginRequest) {
    return this.http.post(this.url + 'login', user)
      .pipe(catchError(this.errorHandler));
  }

  register(newUser: RegisterRequest) {
    return this.http.post(this.url + 'register', newUser)
      .pipe(catchError(this.errorHandler));
  }

  getJobList() {
    return this.http.get(this.url + 'jobs')
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
