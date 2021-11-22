import { TeamMemberShort } from './../../../models/team-member-short.model';
import { Component } from '@angular/core';
import { TeamView } from 'src/app/models/team-view.model';
import { UserView } from 'src/app/models/user-view.model';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';
import { TeamService } from 'src/app/services/teams/team.service';
import { CreateTeamRequest } from 'src/app/models/create-team-request.model';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-team-edit',
  templateUrl: './team-edit.component.html',
  styleUrls: ['./team-edit.component.css']
})
export class TeamEditComponent {

  teamID?: string;
  getTeam?: TeamView;

  TeamName?: string;
  teamNameFormControl = new FormControl('', [Validators.required]);
  TeamLeaderID?: string;
  teamLeaderFormControl = new FormControl();
  TeamLeaderName?: string;
  TeamMembersID: TeamMemberShort[] = [];

  users?: UserView[];
  errorMessage?: string;

  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private teamService: TeamService,
              private router: Router) {


    this.route.params.subscribe(params => {
      this.teamID = params['id'];
      if(this.teamID) {
        this.refreshUsers();
        this.teamService.getTeam(this.teamID).subscribe(data => {
          this.getTeam = data as TeamView;
          this.TeamName = this.getTeam.name;
          this.TeamLeaderID = this.getTeam.teamLeader.id;
          this.TeamLeaderName = this.getTeam.teamLeader.firstname + ' ' +
                this.getTeam.teamLeader.lastname;
          this.getTeam.members.forEach(member => {
            let tempMember: TeamMemberShort = {id: member.id}
            this.TeamMembersID.push(tempMember);
          });
        }, error => {
          this.router.navigate(['/teams']);
        });
      } else {
        this.router.navigate(['/teams']);
      }
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
