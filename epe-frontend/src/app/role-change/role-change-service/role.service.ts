import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {JwtUser} from "../../decoder/decoder-model/jwt-user.interface";
import {JwtService} from "../../decoder/decoder-service/jwt.service";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {User} from "../../components/edit/edit-models/user.interface";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Injectable({providedIn: 'root'})
export class RoleService {

  url: string = 'api-server/api/auth/';
  url2: string = 'api-server/api/users';

  url3: string = 'api-server/api/roles';

  jwtUser?: JwtUser;
  id? : string

  role? : string;

  constructor(private http: HttpClient,
              private jwtService: JwtService,
              private notificationService: ToastrService,
              private router: Router) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser)
      this.id = this.jwtUser.id;
    this.role = this.jwtUser?.role;
  }

  updateUser(user: User | undefined,userId : string | undefined) {
    console.log(user);
    return this.http.put(this.url2 + '/' + userId, user).subscribe( response => {
      this.reload();
      this.notificationService.success('User was edited successfully',
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
    this.router.navigate(['/usersview']);
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

  getUserData(userId: string) {
    return this.http.get(`${this.url2}/${userId}`);
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }

}
