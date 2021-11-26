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

  TeamMemberID?: string;
  teamMemberFormControl = new FormControl();

  TeamMembers: ShortUser[] = [];

  users?: UserView[];

  constructor(@Inject(MAT_DIALOG_DATA) public teamID: string,
              private userService: UserService,
              private teamService: TeamsService,
              private toastr: ToastrService) {

    this.refreshCurrentTeam();
    this.refreshUsers();
  }

  refreshCurrentTeam() {
    this.teamService.getTeam(this.teamID).subscribe(data => {
      this.getTeam = data as TeamView;
      this.TeamMembers = this.getTeam.members; });
  }

  refreshUsers() {
    this.userService.getUsers().subscribe(data => {
      this.users = data as UserView[];
    });
  }

  fillTempTeam() {
    
    if(this.getTeam) {
      let tempTeamName: string = this.getTeam.name;
      let tempTeamLeader: CreateTeamUser = { id: this.getTeam.teamLeader.id};
      let tempTeamMembers: CreateTeamUser[] = [];
      this.getTeam.members.forEach(member => {
        let tempMember: CreateTeamUser = { id: member.id }
        tempTeamMembers.push(tempMember);
      });

      let tempTeam: CreateTeamRequest = {
        name: tempTeamName,
        teamLeader: tempTeamLeader,
        members: tempTeamMembers,
      }

      return tempTeam;
    }

    let tempUndefined: CreateTeamRequest = {
      name: '',
      teamLeader: {id: ''},
      members: []
    }

    return tempUndefined;
  }

  changeTeamName() {

    if(this.getTeam) {
      
      let tempId: string = this.getTeam.id || '';
      let tempTeam: CreateTeamRequest = this.fillTempTeam();
      tempTeam.name = this.TeamName || '';

      this.teamService.updateTeam(tempId, tempTeam).subscribe(data => {
        this.refreshCurrentTeam();
        this.toastr.success('team name updated !', '', {
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

  changeTeamLeader() {
    if(this.getTeam) {
      
      let tempId: string = this.getTeam.id || '';
      let tempTeam: CreateTeamRequest = this.fillTempTeam();
      tempTeam.teamLeader = {id: this.TeamLeaderID || ''}

      this.teamService.updateTeam(tempId, tempTeam).subscribe(data => {
        this.refreshCurrentTeam();
        this.toastr.success('team leader updated !', '', {
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

  updateMembers() {
    if(this.getTeam) {
      
      let tempId: string = this.getTeam.id || '';
      let tempTeam: CreateTeamRequest = this.fillTempTeam();
      tempTeam.members = [];
      this.TeamMembers.forEach(member => {
        let tempMember: CreateTeamUser = { id: member.id }
        if(tempTeam.members)
          tempTeam.members.push(tempMember);
      });

      console.log(tempTeam.members);

      this.teamService.updateTeam(tempId, tempTeam).subscribe(data => {
        this.refreshCurrentTeam();
        this.toastr.success('team members updated !', '', {
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

  removeMember(removeMemberId: string) {
    if(this.TeamMembers)
      this.TeamMembers = this.TeamMembers.filter(member => member.id !== removeMemberId);
  }

  addMember() {
    if(this.users)
      if(this.TeamMembers)
        if(this.TeamMemberID) {
          let newUser: UserView = this.users.find(member => member.id == this.TeamMemberID) || this.createEmptyUserView();
          let newMember: ShortUser = {
            id: newUser.id,
            firstname: newUser.firstname,
            lastname: newUser.lastname
          }
          this.TeamMembers.push(newMember);
      }
  }

  createEmptyUserView() {
    let temp: UserView = {
      id: '',
      email: '',
      firstname: '',
      lastname: '',
      birthDate: '',
      employmentDate: '',
      phoneNumber: '',
      job: '',
      bio: '',
      buddyId: '',
      token: '',
    }

    return temp;
  }
}
