import { CreateTeamUser } from './../teams-model/create-team-user.interface';
import { CreateTeamRequest } from './../teams-model/create-team-request.interface';
import { TeamView } from './../teams-model/team-view.interface';
import { Component, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { UserView } from '../teams-model/user-view.interface';
import { TeamsService } from '../teams-service/teams.service';
import { UserService } from '../user-service/user.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-team-edit',
  templateUrl: './team-edit.component.html',
  styleUrls: ['./team-edit.component.css']
})
export class TeamEditComponent {

  getTeam?: TeamView;

  TeamName?: string;
  teamNameFormControl = new FormControl('', [Validators.required]);
  TeamLeaderID?: string;
  teamLeaderFormControl = new FormControl();
  TeamLeaderName?: string;
  TeamMembersID: CreateTeamUser[] = [];

  users?: UserView[];
  errorMessage?: string;

  constructor(@Inject(MAT_DIALOG_DATA) public teamID: string,
              private userService: UserService,
              private teamService: TeamsService) {

    this.refreshUsers();
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
    });   
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
    }, error => {
      this.errorMessage = error.error.title;
    });
  }
}
