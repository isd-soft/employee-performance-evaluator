import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {JwtUser} from "../../decoder/decoder-model/jwt-user.interface";
import {JwtService} from "../../decoder/decoder-service/jwt.service";
import {User} from "../../edit/edit-models/user.interface";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";

@Injectable({providedIn: 'root'})
export class RoleService {

  url: string = 'api-server/api/auth/';
  url2: string = 'api-server/api/users';

  url3: string = 'api-server/api/roles';

  jwtUser?: JwtUser;
  id? : string

  role? : string;

  constructor(private http: HttpClient, private jwtService: JwtService) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser)
      this.id = this.jwtUser.id;
    this.role = this.jwtUser?.role;
  }

  update(user: User | undefined) {
    return this.http.put(this.url2 + '/' + this.id, user)
      .pipe(catchError(this.errorHandler));
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
