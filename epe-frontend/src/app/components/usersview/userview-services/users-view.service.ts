import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import {User} from "../userview-models/User";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";

@Injectable({
  providedIn: 'root'
})

export class UserviewsServices {

  url: string = 'api-server/api/users';

  jwtUser?: JwtUser;
  role? : string

  constructor(private http: HttpClient, private jwtService: JwtService) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser)
      this.role = this.jwtUser.role;
  }

  getUserList() {
    return this.http.get(this.url);
  }

  deleteUser(userId : string | undefined) {
    console.log(userId);
    return this.http.delete(this.url + '/' + userId).subscribe();
  }

  getRole() {
    return this.role;
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
