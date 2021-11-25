import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TeamView } from '../teams-model/team-view.interface';
import { TeamsService } from '../teams-service/teams.service';

@Component({
  selector: 'app-team-view',
  templateUrl: './team-view.component.html',
  styleUrls: ['./team-view.component.css']
})
export class TeamViewComponent {

  getTeam?: TeamView;

  constructor(@Inject(MAT_DIALOG_DATA) public teamID: string, private teamService: TeamsService) {

    this.teamService.getTeam(this.teamID).subscribe(data => {
      this.getTeam = data as TeamView; });   
  }
}
