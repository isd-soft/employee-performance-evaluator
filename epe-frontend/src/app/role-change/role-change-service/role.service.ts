import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {JwtUser} from "../../decoder/decoder-model/jwt-user.interface";
import {JwtService} from "../../decoder/decoder-service/jwt.service";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {User} from "../../components/edit/edit-models/user.interface";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {environment} from "../../../environments/environment";

@Injectable({providedIn: 'root'})
export class RoleService {

  baseUrl = environment.baseUrl;

  url: string = this.baseUrl + 'api/auth/';
  url2: string = this.baseUrl + 'api/users';
  url3: string =  this.baseUrl + 'api/roles';

  jwtUser?: JwtUser;
  id? : string

  role? : string;
  requiredRole: string = "SYS_ADMIN";

  currentUrl?: string;


  constructor(private http: HttpClient,
              private jwtService: JwtService,
              private notificationService: ToastrService,
              private dialogRef : MatDialog,
              private router: Router) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser) {
      this.id = this.jwtUser.id;
      this.role = this.jwtUser?.role;
    }
    this.currentUrl = this.router.url;
  }

  updateUser(user: User | undefined,userId : string | undefined,currentUserId: string | undefined) {
    console.log(user);
    let currentUrl : string;
    if (userId == currentUserId) {
      currentUrl = '/my-profile';
    } else {
      currentUrl = "/usersview"
    }
    return this.http.put(this.url2 + '/' + userId, user).subscribe( response => {
      this.closeDialogs();
      this.reload(currentUrl);
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


  reload(currentUrl : string | undefined) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([currentUrl]);
    console.log(this.currentUrl);
  }


  getUser() {
    return this.http.get(this.url2 + '/' + this.id)
      .pipe(catchError(this.errorHandler));
  }

  getBuddies(userId : string | undefined) {
    return this.http.get(this.url2 + '/' + userId + '/buddies')
  }

  getRole() {
    return this.role;
  }

  closeDialogs() {
    this.dialogRef.closeAll();
  }

  getJobList() {
    return this.http.get(this.url + 'jobs')
  }

  getRoles() {
    // if (this.role == this.requiredRole) {
    //   return this.http.get(this.url3);
    // }
    // return 'user';
    return this.http.get(this.url3);
  }

  getUserData(userId: string) {
    return this.http.get(`${this.url2}/${userId}`);
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }

}
