import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';

@Injectable({
  providedIn: 'root'
})
export class HomeGuardService {

  loggedUser?: JwtUser;

  constructor(private jwtService: JwtService,
              private router: Router) {
    
    this.loggedUser = jwtService.getJwtUser();
  }

  isLoggedIn(): boolean {

    if(this.loggedUser == null) {
      return true;
    }
    else {
      this.router.navigate(['/dashboard']);
      return false;
    }
  } 
}
