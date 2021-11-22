import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CreateTeamRequest } from 'src/app/models/create-team-request.model';
import { UserView } from 'src/app/models/user-view.model';
import { TeamService } from 'src/app/services/teams/team.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-team-new',
  templateUrl: './team-new.component.html',
  styleUrls: ['./team-new.component.css']
})
export class TeamNewComponent  {

  newTeamName?: string = '';
  teamNameFormControl = new FormControl('', [Validators.required]);
  newTeamLeaderId?: string = '';
  teamLeaderFormControl = new FormControl();
  users?: UserView[];
  errorMessage?: string;

  constructor(private teamService: TeamService,
    private userService: UserService,
    private router: Router) {

      this.refreshUsers();
  }

  refreshUsers() {
    this.userService.getUsers().subscribe(data => {
      this.users = data as UserView[];
    });
  }

  setTeamLeader(id: string) {
    this.newTeamLeaderId = id;
  }

  createTeam() {


    if((this.newTeamName == '') || (this.newTeamLeaderId == '')) {
      this.errorMessage = 'please complete all details'
    } else {
      let newTeam: CreateTeamRequest = {
        name: this.newTeamName || '',
        teamLeader: {
          id: this.newTeamLeaderId || ''
        }
      }
  
      this.teamService.createTeam(newTeam).subscribe(data => {
      }, error => {
         if(error.status == 201) {
          this.router.navigate(['/teams']);
         } 
         this.errorMessage = error.error.title;})
    }
  }
}
