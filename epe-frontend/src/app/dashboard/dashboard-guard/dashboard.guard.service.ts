import { Injectable, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';

@Injectable({
  providedIn: 'root'
})
export class DashboardGuardService implements OnInit, OnDestroy {

  dashboardUser?: JwtUser

  constructor(private jwtService: JwtService, private router: Router) {
    this.dashboardUser = this.jwtService.getJwtUser()
   }

  ngOnInit(): void {
    
  }

  ngOnDestroy(): void {
    console.log('whoopsie')
    this.dashboardUser = undefined
  }

  isLoggedIn(): boolean {

    if(this.dashboardUser) {
      return true
    }
    else {
      this.router.navigate(['/register'])
      return false
    }
  } 
}
