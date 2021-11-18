import { JobItem } from './../edit-models/job-item.interface';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { UpdateRequest } from '../edit-models/update-request.interface';
import { User } from '../edit-models/user.interface';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';


@Injectable({providedIn : 'root'})
export class EditService {

  url: string = 'api-server/api/auth/';
  url2: string = 'api-server/api/users';

  jwtUser?: JwtUser;
  id? : string

  constructor(private http: HttpClient, private jwtService: JwtService) { 
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser)
    this.id = this.jwtUser.id;
  }

  update(user: UpdateRequest) {
    console.log(user)
    return this.http.put(this.url2 + '/' + this.id, user)
      .pipe(catchError(this.errorHandler));
  }

  getUser(user: User) {
    return this.http.get(this.url2 + '/' + this.id)
    .pipe(catchError(this.errorHandler));
  }

  getJobList() {
    return this.http.get(this.url + 'jobs')
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
