import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { UserLoginRequest } from '../login-models/user-login-request.interface';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url: string = 'http://localhost:8098/api/v1/auth/'

  constructor(private http: HttpClient) { }

  login(user: UserLoginRequest) {
    return this.http.post(this.url + 'login', user)
      .pipe(catchError(this.errorHandler))
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error.message || "Server Error");
  }
}
