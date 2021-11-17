import { UserView } from './../teams-model/user-view.interface';
import { TeamView } from './../teams-model/team-view.interface';
import { TeamsService } from './../teams-service/teams.service';
import { Component, OnInit } from '@angular/core';
import { TeamShow } from '../teams-model/team-show.interface';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {

  teams?: TeamView[];
  users?: UserView[];

  constructor(private teamService: TeamsService) {

    this.refreshTeams();
    this.refreshUsers();
   }


   refreshTeams() {
    this.teamService.getTeams().subscribe(data => {
      this.teams = data as TeamView[] });
   }

   refreshUsers() {
    this.teamService.getUsers().subscribe(data => {
      this.users = data as UserView[] });
   }
}
