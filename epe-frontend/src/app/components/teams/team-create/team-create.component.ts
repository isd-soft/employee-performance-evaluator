import { CreateTeamRequest } from './../teams-model/create-team-request.interface';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TeamsService } from '../teams-service/teams.service';
import { UserService } from '../user-service/user.service';
import { UserView } from './../teams-model/user-view.interface';

@Component({
  selector: 'app-team-create',
  templateUrl: './team-create.component.html',
  styleUrls: ['./team-create.component.css']
})
export class TeamCreateComponent {

  newTeamName?: string = '';
  newTeamLeaderId?: string;

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

    let newTeam: CreateTeamRequest = this.createNewTeamFromInputDetails();
    this.teamService.createTeam(newTeam).subscribe(data => {
    }, error => {
       if(error.status == 201) {
        this.router.navigate(['/teams']);
       } 
       this.errorMessage = error.error.title;})
  }

  createNewTeamFromInputDetails(): CreateTeamRequest {
  
    let tempTeam: CreateTeamRequest = {
      name: this.newTeamName || '',
      teamLeader: {
        id: this.newTeamLeaderId || ''
      },
      teamMembers: []
    }

    return tempTeam;
  }
}
