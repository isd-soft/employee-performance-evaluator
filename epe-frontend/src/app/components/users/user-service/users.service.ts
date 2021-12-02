import { CreateUserRequest } from './../user-models/create-user.interface';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  baseUrl = environment.baseUrl;
  userUrl: string = this.baseUrl + 'api/users';

  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http.get(this.userUrl)
      .pipe(catchError(this.errorHandler));
  }

  getUser(id: string) {
    return this.http.get(this.userUrl + '/' + id)
    .pipe(catchError(this.errorHandler));
  }

  createUser(user: CreateUserRequest) {
    return this.http.post(this.userUrl, user)
    .pipe(catchError(this.errorHandler));
  }

  updateUser(id: string, user: CreateUserRequest) {
    return this.http.put(this.userUrl + '/' + id, user)
    .pipe(catchError(this.errorHandler));
  }

  deleteUser(id: string) {
    return this.http.delete(this.userUrl + '/' + id)
    .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
