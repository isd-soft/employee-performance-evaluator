import { JwtUser } from './../../../decoder/decoder-model/jwt-user.interface';
import { JwtService } from './../../../decoder/decoder-service/jwt.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  profileUrl = 'api-server/api/users';

  jwtUser: JwtUser

  constructor(private http: HttpClient,
              private jwtService: JwtService) { 
  
    this.jwtUser = this.jwtService.getJwtUser();
  }

  getMyProfile() {
      return this.http.get(this.profileUrl + '/' + this.jwtUser.id)
      .pipe(catchError(this.errorHandler));
  }

  getMyTeams() {
    return this.http.get(this.profileUrl + '/' + this.jwtUser.id + '/team')
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
