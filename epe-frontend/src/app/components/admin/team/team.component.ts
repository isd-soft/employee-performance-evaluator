import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TeamView } from 'src/app/models/team-view.model';
import { TeamService } from 'src/app/services/teams/team.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent {

  teams?: TeamView[];

  displayedColumns: string[] = ['name', 'teamLeader', 'action'];
  dataSource: any;

  constructor(private teamService: TeamService,
    private router: Router) {

    this.refreshTeams();
   }

   refreshTeams() {
    this.teamService.getTeams().subscribe(data => {
      this.teams = data as TeamView[];
      this.dataSource = new MatTableDataSource(this.teams); });
   }

   applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
