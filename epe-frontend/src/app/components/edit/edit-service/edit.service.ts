import { JobItem } from '../edit-models/job-item.interface';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { UpdateRequest } from '../edit-models/update-request.interface';
import { User } from '../edit-models/user.interface';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";


@Injectable({providedIn : 'root'})
export class EditService {

  url: string = 'api-server/api/auth/';
  url2: string = 'api-server/api/users';

  url3: string = 'api-server/api/roles';

  jwtUser?: JwtUser;
  id? : string

  role? : string;

  constructor(private http: HttpClient,
              private jwtService: JwtService,
              private router : Router,
              private notificationService: ToastrService) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser)
    this.id = this.jwtUser.id;
    this.role = this.jwtUser?.role;
  }

  update(user: User) {
    return this.http.patch(this.url2 + '/' + this.id, user).subscribe( response => {
      this.reload();
      this.notificationService.success('Profile was edited successfully',
        '', {
          timeOut: 3000,
          progressBar: true
        });
    }, error => {
      let message: string;
      switch (error.status) {
        case 400:
          message = "Bad request. " + error.error.title;
          break;
        case 401:
          message = "Unauthorized"
          break;
        case 500:
          message = "Internal server error"
          break;
        default:
          message = "Unknown error"
      }
      this.notificationService.error(message,
        '', {
          timeOut: 3000,
          progressBar: true
        });
    });
  }

  reload() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/profile']);
  }

  getUser() {
    return this.http.get(this.url2 + '/' + this.id)
    .pipe(catchError(this.errorHandler));
  }


  getRole() {
    return this.role;
  }

  getJobList() {
    return this.http.get(this.url + 'jobs')
  }

  getRoles() {
    return this.http.get(this.url3);
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
