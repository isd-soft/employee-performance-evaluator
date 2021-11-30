import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { ShortUser } from '../../teams/teams-model/short-user.interface';
import { UserviewsServices } from './users-view.service';

@Injectable({
  providedIn: 'root'
})
export class MyTeamResolverService implements Resolve<ShortUser[]> {

  constructor(private userviewsServices: UserviewsServices) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ShortUser[]> {
    return this.userviewsServices.getTeamMembersArray();
  }
}
