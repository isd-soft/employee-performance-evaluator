import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';

@Injectable({
  providedIn: 'root'
})
export class DashboardGuardService {

  loggedUser?: JwtUser;

  constructor(private jwtService: JwtService,
              private router: Router) {

    this.loggedUser = this.jwtService.getJwtUser();
  }

  isLoggedIn(): boolean {

    if(!(this.loggedUser == null)) {
      return true;
    }
    else {
      this.router.navigate(['/home']);
      return false;
    }
  }
}
