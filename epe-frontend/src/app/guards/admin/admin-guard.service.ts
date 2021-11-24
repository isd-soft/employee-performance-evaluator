import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuardService {

  loggedUser?: JwtUser;

  constructor(private jwtService: JwtService,
              private router: Router) {

    this.loggedUser = this.jwtService.getJwtUser();
  }

  isAdmin(): boolean {

    if((!(this.loggedUser == null)) && (this.loggedUser.role == 'ROLE_ADMIN' || this.loggedUser.role == "ROLE_SYSADMIN")) {
      return true;
    }
    else {
      this.router.navigate(['/home']);
      return false;
    }
  }
}
