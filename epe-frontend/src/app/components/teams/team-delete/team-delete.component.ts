import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TeamsComponent } from '../team-component/teams.component';
import { TeamView } from '../teams-model/team-view.interface';
import { TeamsService } from '../teams-service/teams.service';

@Component({
  selector: 'app-team-delete',
  templateUrl: './team-delete.component.html',
  styleUrls: ['./team-delete.component.css']
})
export class TeamDeleteComponent {

  getTeam?: TeamView;

  constructor(@Inject(MAT_DIALOG_DATA) public teamID: string,
              public dialogRef: MatDialogRef<TeamsComponent>,
              private teamService: TeamsService) {

    this.teamService.getTeam(this.teamID).subscribe(data => {
      this.getTeam = data as TeamView; });   
  }

  deleteTeam() {
        this.teamService.deleteTeam(this.teamID).subscribe();
        this.dialogRef.close();
  }

}
