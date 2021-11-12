import { JwtService } from './../../decoder/decoder-service/jwt.service';
import { Injectable } from '@angular/core';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedUser?: JwtUser

  constructor(private jwtService: JwtService,
              private router: Router) {
    this.loggedUser = jwtService.getJwtUser()
  }

  isLoggedIn(): boolean {
    
    if(this.loggedUser) {
      return true
    }
    else {
      this.router.navigate(['/login'])
      return false
    }
  } 
}
