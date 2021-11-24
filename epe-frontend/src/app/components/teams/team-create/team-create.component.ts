import { CreateTeamRequest } from './../teams-model/create-team-request.interface';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserView } from './../teams-model/user-view.interface';
import { UserService } from '../user-service/user.service';
import { FormControl, Validators } from '@angular/forms';
import { TeamsService } from '../teams-service/teams.service';

@Component({
  selector: 'app-team-create',
  templateUrl: './team-create.component.html',
  styleUrls: ['./team-create.component.css']
})
export class TeamCreateComponent {

  newTeamName?: string = '';
  teamNameFormControl = new FormControl('', [Validators.required]);
  newTeamLeaderId?: string = '';
  teamLeaderFormControl = new FormControl();
  users?: UserView[];
  errorMessage?: string;

  constructor(private teamService: TeamsService,
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
