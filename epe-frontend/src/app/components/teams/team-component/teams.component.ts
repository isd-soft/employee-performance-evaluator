import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { TeamView } from '../teams-model/team-view.interface';
import { TeamsService } from '../teams-service/teams.service';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {

  teams?: TeamView[];

  displayedColumns: string[] = ['name', 'teamLeader', 'action'];
  dataSource: any;

  constructor(private teamService: TeamsService,
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

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
