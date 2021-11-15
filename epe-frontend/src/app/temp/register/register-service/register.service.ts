import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { RegisterRequest } from '../register-models/register-request.interface';
import { Job } from '../register-models/job';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  url: string = 'api-server/api/auth/'

  jobList: Job[] = [
    {
      name: 'developer'
    }
  ]

  constructor(private http: HttpClient) { }

  register(newUser: RegisterRequest) {
    return this.http.post(this.url + 'register', newUser)
      .pipe(catchError(this.errorHandler))
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error.message || "Server Error");
  }

  getJobs(): Job[] {
    return this.jobList
  }
}
