import { CreateTeamUser } from './../teams-model/create-team-user.interface';
import { CreateTeamRequest } from './../teams-model/create-team-request.interface';
import { TeamView } from './../teams-model/team-view.interface';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserView } from '../teams-model/user-view.interface';
import { TeamsService } from '../teams-service/teams.service';
import { UserService } from '../user-service/user.service';

@Component({
  selector: 'app-team-edit',
  templateUrl: './team-edit.component.html',
  styleUrls: ['./team-edit.component.css']
})
export class TeamEditComponent {

  teamID?: string;
  getTeam?: TeamView;

  TeamName?: string;
  TeamLeaderID?: string;
  TeamLeaderName?: string;
  TeamMembersID: CreateTeamUser[] = [];

  users?: UserView[];
  errorMessage?: string;

  constructor(private teamService: TeamsService,
    private userService: UserService,
    private router: Router) { 

      this.getTeamID();
      this.refreshUsers();

      if(this.teamID) {
        this.teamService.getTeam(this.teamID).subscribe(data => {
          this.getTeam = data as TeamView;
          
          this.TeamName = this.getTeam.name;
          this.TeamLeaderID = this.getTeam.teamLeader.id;
          this.TeamLeaderName = this.getTeam.teamLeader.firstname + ' ' +
                this.getTeam.teamLeader.lastname;
          
          this.getTeam.members.forEach(member => {
            let tempMember: CreateTeamUser = {id: member.id}
            this.TeamMembersID.push(tempMember);
          });
          

        }, error => {
          this.router.navigate(['/teams']);
        });
      } else {
        this.router.navigate(['/teams']);
      } 
  }

  getTeamID() {
    this.teamID = localStorage.getItem("TEAM_ID") || '';
    localStorage.removeItem("TEAM_ID");
  }

  refreshUsers() {
    this.userService.getUsers().subscribe(data => {
      this.users = data as UserView[];
    });
  }

  setTeamLeader(id: string) {
    this.TeamLeaderID = id;
  }

  updateTeam() {

    let temp: CreateTeamRequest = {
      name : this.TeamName || '',
      teamLeader: {id: this.TeamLeaderID || '' },
      teamMembers: this.TeamMembersID
    }

    this.teamService.updateTeam(this.teamID || '', temp).subscribe(data => {
      this.router.navigate(['/teams']);
    }, error => {
      this.errorMessage = error.error.title;
    });
  }

  deleteTeam() {

    this.teamService.deleteTeam(this.teamID || '').subscribe(data => {
      this.router.navigate(['/teams']);
    }, error => {
      if(error.status == 200) {
        this.router.navigate(['/teams']);
       } 
      this.errorMessage = error.error.title;
    });
  }
}
