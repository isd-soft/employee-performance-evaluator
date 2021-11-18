import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import {User} from '../user-model/User'


@Injectable({
  providedIn: 'root'
})
export class UserService {

  url: string = 'api-server/api/users/655445a3-7676-448a-bc81-f10cbc31fbd8';
  userID?: string;


  constructor(private http: HttpClient) {
    this.url = this.url ;

  }

  getUser() {
   return this.http.get(this.url);
  }


  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
