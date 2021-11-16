import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TeamsService {

  teamUrl: string = 'api-server/api/teams';
  userUrl: string = 'api-server/api/users';

  constructor(private http: HttpClient) { }

  getTeams() {
    return this.http.get(this.teamUrl)
      .pipe(catchError(this.errorHandler));
  }

  getUsers() {
    return this.http.get(this.userUrl)
    .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
