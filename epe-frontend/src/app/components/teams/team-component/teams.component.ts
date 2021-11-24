import {Component, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from "@angular/material/dialog";
import { TeamView } from '../teams-model/team-view.interface';
import { TeamsService } from '../teams-service/teams.service';
@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {

  displayedColumns: string[] = ['name', 'teamLeader', 'buttons'];
  // @ts-ignore
  dataSource: MatTableDataSource<TeamView>;
  teams?: TeamView[];

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(private teamService: TeamsService, public dialog: MatDialog){
    this.reloadTeams();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  reloadTeams() {
    this.teamService.getTeams().subscribe(data => {
      this.teams = data as TeamView[];
      this.dataSource = new MatTableDataSource(this.teams);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });

  }

  // onView(user : string) {
  //   this.dialog.open( UserComponent, {data: user} );
  // }

  // edit(user : string) {
  //   console.log('edit');
  //   // this.dialog.open(RoleChangeComponent, {height:'100%',width:'70%', data : user});
  //   // this.dialog.afterAllClosed.
  // }

  // delete(user : string) {
  //   console.log('delete');
  //   // this.userview.deleteUser(user);
  //   // this.reloadComponent();
  // }
  // reloadComponent() {
  //   // @ts-ignore
  //   let currentUrl = this.router.url;
  //   // @ts-ignore
  //   this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  //   // @ts-ignore
  //   this.router.onSameUrlNavigation = 'reload';
  //   // @ts-ignore
  //   this.router.navigate([currentUrl]);
  // }
}
