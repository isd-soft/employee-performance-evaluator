import { Jwt } from './../../models/jwt.model';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { JwtService } from 'src/app/jwt/jwt-service.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  loggedUser?: Jwt;

  constructor(private jwtService: JwtService,
    private router: Router) {

    this.loggedUser = this.jwtService.getJwtUser();
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
      if((!(this.loggedUser == null)) && (this.loggedUser.role == 'ROLE_ADMIN')) {
        return true;
      }
      else {
        this.loggedUser = this.jwtService.getJwtUser();
        this.router.navigate(['/dashboard']);
        return false;
      }
  } 
}
