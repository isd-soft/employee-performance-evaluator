import {Injectable} from "@angular/core";
import {JwtUser} from "../../decoder/decoder-model/jwt-user.interface";
import {JwtService} from "../../decoder/decoder-service/jwt.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class SysadminGuardService {

  loggedUser?: JwtUser;

  constructor(private jwtService: JwtService,
              private router: Router) {

    this.loggedUser = this.jwtService.getJwtUser();
  }

  isSysadmin(): boolean {

    console.log(this.loggedUser?.role);

    if((this.loggedUser) && (this.loggedUser.role == 'ROLE_SYSADMIN')) {
      return true;
    }
    else {
      this.router.navigate(['/home']);
      return false;
    }
  }

}
