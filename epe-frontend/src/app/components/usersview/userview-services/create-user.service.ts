import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {Injectable} from "@angular/core";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {NewUser} from "../userview-models/NewUser";

@Injectable({providedIn : 'root'})
export class CreateUserService {

  url: string = 'api-server/api/users';
  url2: string = 'api-server/api/auth/';


  jwtUser?: JwtUser;
  id? : string

  constructor(private http: HttpClient,
              private jwtService: JwtService,
              private notificationService: ToastrService,
              private router: Router,
              private dialogRef: MatDialog) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser)
      this.id = this.jwtUser.id;
  }

    createUser(userDto: NewUser) {
    return this.http.post(this.url2 + 'register',userDto).subscribe( response => {
      this.closeDialogs();
      this.reload();
      this.notificationService.success('User was created successfully',
        '', {
          timeOut: 3000,
          progressBar: true
        });
    }, error => {
      console.log(error)
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

  closeDialogs() {
    this.dialogRef.closeAll();
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
