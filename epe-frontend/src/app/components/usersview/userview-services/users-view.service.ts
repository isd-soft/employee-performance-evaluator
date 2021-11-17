import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import {User} from "../userview-models/User";



@Injectable({
  providedIn: 'root'
})
export class UserviewsServices {

  url: string = 'api-server/api/users';

  constructor(private http: HttpClient) { }

  getUserList() {
    return this.http.get<User[]>(this.url)
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
