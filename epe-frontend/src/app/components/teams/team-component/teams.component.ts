import { Router } from '@angular/router';
import { TeamView } from './../teams-model/team-view.interface';
import { TeamsService } from './../teams-service/teams.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {

  teams?: TeamView[];

  constructor(private teamService: TeamsService,
    private router: Router) {

    this.refreshTeams();
   }

   refreshTeams() {
    this.teamService.getTeams().subscribe(data => {
      this.teams = data as TeamView[] });
   }

   createNewTeam() {
    this.router.navigate(['/team-create']);
   }

   editTeam(id: string) {
     localStorage.setItem("TEAM_ID", id);
     this.router.navigate(['/team-edit']);
   }
}
