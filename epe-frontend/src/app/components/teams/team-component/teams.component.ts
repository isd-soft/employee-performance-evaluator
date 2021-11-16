import { UserView } from './../teams-model/user-view.interface';
import { TeamView } from './../teams-model/team-view.interface';
import { TeamsService } from './../teams-service/teams.service';
import { Component } from '@angular/core';
import { TeamShow } from '../teams-model/team-show.interface';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {

  teams?: TeamView[];
  users?: UserView[];
  teamShow?: TeamShow[] = [];

  constructor(private teamService: TeamsService) {
    
    this.teamService.getTeams().subscribe(data => {
      this.teams = data as TeamView[] });

    this.teamService.getUsers().subscribe(data => {
      this.users = data as UserView[] });

    this.fillTeamShow();

    console.log(this.teamShow)
   }

   fillTeamShow() {

    if(this.teams && this.users)
    this.teams.forEach((team: TeamView) => {

      let temp_id = team.id;
      let temp_name = team.name;
      let teamLeaderId = team.teamLeaderId
      let temp_teamLeaderName = this.users?.find(x => x.id == teamLeaderId)?.firstname + ' ' + 
        this.users?.find(x => x.id == teamLeaderId)?.lastname;

      let temp_team: TeamShow = {
        id: temp_id,
        name: temp_name,
        teamLeaderName: temp_teamLeaderName,
        membersNames: []
      }

      this.teamShow?.push(temp_team);
      
    });
   }
}
