import { RegisterRequest } from './../../models/register-request.model';
import { Injectable } from '@angular/core';
import { LoginRequest } from 'src/app/models/login-request.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  url: string = 'api-server/api/auth/';

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
    return this.http.get(this.url + 'jobs');
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
