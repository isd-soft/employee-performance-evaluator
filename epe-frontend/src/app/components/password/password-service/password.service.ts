import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {Injectable} from "@angular/core";
import {PasswordTemplate} from "../password-model/password.interface";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Injectable({providedIn : 'root'})
export class PasswordService {

  url: string = 'api-server/api/users';

  jwtUser?: JwtUser;
  id? : string

  constructor(private http: HttpClient,
              private jwtService: JwtService,
              private notificationService: ToastrService,
              private router: Router) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser)
      this.id = this.jwtUser.id;
  }

  changePassword(passwordDto: PasswordTemplate) {
    return this.http.put(this.url + '/password/' + this.id,passwordDto).subscribe( response => {
      this.reload();
      this.notificationService.success('Password was updated successfully',
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

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
