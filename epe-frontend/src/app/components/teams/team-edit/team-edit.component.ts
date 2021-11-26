import { CreateTeamUser } from './../teams-model/create-team-user.interface';
import { CreateTeamRequest } from './../teams-model/create-team-request.interface';
import { TeamView } from './../teams-model/team-view.interface';
import { Component, Inject } from '@angular/core';
import { UserView } from '../teams-model/user-view.interface';
import { TeamsService } from '../teams-service/teams.service';
import { UserService } from '../user-service/user.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ShortUser } from '../teams-model/short-user.interface';
import { ShortUserSelected } from '../teams-model/short-user-selected';

@Component({
  selector: 'app-team-edit',
  templateUrl: './team-edit.component.html',
  styleUrls: ['./team-edit.component.css']
})
export class TeamEditComponent {

  getTeam?: TeamView;

  NewTeamName?: string;
  teamNameFormControl = new FormControl();

  NewTeamLeaderID?: string;
  teamLeaderFormControl = new FormControl();

  teamMembersFormControl = new FormControl();
  selectedMembers?: any[] = [];

  CurrentTeamMembers: ShortUser[] = [];
  users?: UserView[];

  constructor(@Inject(MAT_DIALOG_DATA) public teamID: string,
              private userService: UserService,
              private teamService: TeamsService,
              private toastr: ToastrService) {

    this.refreshCurrentTeam().then(res => this.refreshUsers());
  }

  refreshCurrentTeam() {
    this.teamService.getTeam(this.teamID).subscribe(data => {
      this.getTeam = data as TeamView;
      this.CurrentTeamMembers = this.getTeam.members; });
    return new Promise<void>((resolve, reject) => {resolve(); });
  }

  refreshUsers() {
    this.userService.getUsers().subscribe(data => { 
      this.users = data as UserView[];
      this.psuhTeamMembersToList();
     });
    return new Promise<void>((resolve, reject) => {resolve(); });
  }

  psuhTeamMembersToList() {

    if(this.users) {
      this.users.forEach(user => {
        if(this.CurrentTeamMembers.find(member => member.id == user.id) && this.selectedMembers)
          this.selectedMembers.push(user.id);
      });
    }
  }

  updateTeam() {

    if(this.getTeam) {
      let tempTeamName = this.NewTeamName ? this.NewTeamName : this.getTeam.name;
      let tempTeamLeader: CreateTeamUser = { id : this.NewTeamLeaderID ? this.NewTeamLeaderID : this.getTeam.teamLeader.id };
      let tempTeamMembers: CreateTeamUser[] = [];
      
      if(this.selectedMembers) {
        this.selectedMembers.forEach(member => {
          let tempMember: CreateTeamUser = { id: member };
          tempTeamMembers.push(tempMember);
        });
      }
      
      let tempTeam: CreateTeamRequest = {
        name: tempTeamName,
        teamLeader: tempTeamLeader,
        members: tempTeamMembers
      }

      this.teamService.updateTeam(this.getTeam.id || '', tempTeam).subscribe(data => {
        this.refreshCurrentTeam();
        this.toastr.success('team details updated !', '', {
          timeOut: 2000,
          progressBar: true
        });;
      }, error => {
        this.toastr.error('something went wrong ..','', {
          timeOut: 2000,
          progressBar: true
        })
      });
    }
  }
}
