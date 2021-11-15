import { JobItem } from './../home-models/job-item.interface';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { RegisterRequest } from '../home-models/register-request.interface';
import { LoginRequest } from '../home-models/login-request.interface';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class HomeService {

  url: string = 'api-server/api/auth/';

  jobList: JobItem[] = [
    {
      name: 'Java Developer'
    }
  ]

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
    return this.jobList;
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error.status);
  }
}
