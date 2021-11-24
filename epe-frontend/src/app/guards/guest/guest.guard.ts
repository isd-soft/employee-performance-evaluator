import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { JwtService } from 'src/app/jwt/jwt-service.service';
import { Jwt } from 'src/app/models/jwt.model';

@Injectable({
  providedIn: 'root'
})
export class GuestGuard implements CanActivate {

  loggedUser?: Jwt;

  constructor(private jwtService: JwtService,
              private router: Router) {
    
    this.loggedUser = this.jwtService.getJwtUser();
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
      if(this.loggedUser == null) {
        return true;
      }
      else {
        this.loggedUser = this.jwtService.getJwtUser();
        this.router.navigate(['/dashboard']);
        return false;
      }
  }
  
}
