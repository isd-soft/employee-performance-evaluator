import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { CreateTeamRequest } from '../teams-model/create-team-request.interface';
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TeamsService {

  baseUrl = environment.baseUrl;
  teamUrl: string = this.baseUrl + 'api/teams';

  constructor(private http: HttpClient) { }

  getTeam(id: string) {
    return this.http.get(this.teamUrl + '/' + id)
      .pipe(catchError(this.errorHandler));
  }

  getTeams() {
    return this.http.get(this.teamUrl)
      .pipe(catchError(this.errorHandler));
  }

  createTeam(team: CreateTeamRequest) {
    return this.http.post(this.teamUrl, team)
      .pipe(catchError(this.errorHandler));
  }

  updateTeam(id:string, team: CreateTeamRequest) {
    return this.http.put(this.teamUrl + '/' + id, team)
      .pipe(catchError(this.errorHandler));
  }

  deleteTeam(id: string) {
    return this.http.delete(this.teamUrl + '/' + id)
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
