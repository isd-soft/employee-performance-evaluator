import { Injectable, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';

@Injectable({
  providedIn: 'root'
})
export class DashboardService implements OnInit, OnDestroy {

  loggedUser?: JwtUser

  constructor(private jwtService: JwtService, private router: Router) { }

  ngOnInit(): void {
    this.loggedUser = this.jwtService.getJwtUser()
  }

  ngOnDestroy(): void {
    this.loggedUser = undefined
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
