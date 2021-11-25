import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {SysadminGuardService} from "./sysadmin-guard.service";

@Injectable({
  providedIn: 'root'
})
export class SysadminGuard implements CanActivate {

  constructor(private guard: SysadminGuardService) {}

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.guard.isSysadmin();
  }

}
