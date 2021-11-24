import {Component, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from "@angular/material/dialog";
import { TeamView } from '../teams-model/team-view.interface';
import { TeamsService } from '../teams-service/teams.service';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { FormControl, Validators } from '@angular/forms';
import { UserView } from '../teams-model/user-view.interface';
import { UserService } from '../user-service/user.service';
import { CreateTeamRequest } from '../teams-model/create-team-request.interface';

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

  newTeamName?: string;
  teamNameFormControl = new FormControl('', [Validators.required]);
  newTeamLeaderId?: string;
  teamLeaderFormControl = new FormControl();
  users?: UserView[];
  errorMessage?: string;

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(private teamService: TeamsService, public dialog: MatDialog, private userService: UserService){
    this.reloadAll();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  reloadAll() {
    this.newTeamName = undefined;
    this.teamNameFormControl.setErrors(null);
    this.newTeamLeaderId = undefined;
    this.teamLeaderFormControl.setErrors(null);
    this.errorMessage = undefined;
    this.reloadTeams();
    this.refreshUsers();
  }

  reloadTeams() {
    this.teamService.getTeams().subscribe(data => {
      this.teams = data as TeamView[];
      this.dataSource = new MatTableDataSource(this.teams);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
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
          // this.router.navigate(['/teams']);
         } 
         this.errorMessage = error.error.title;})
    }
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
