import { ActivatedRoute, Router } from '@angular/router';
import { TeamView } from './../teams-model/team-view.interface';
import {Component, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from "@angular/material/dialog";
import { TeamsService } from '../teams-service/teams.service';
import { FormControl, Validators } from '@angular/forms';
import { UserView } from '../teams-model/user-view.interface';
import { UserService } from '../user-service/user.service';
import { CreateTeamRequest } from '../teams-model/create-team-request.interface';
import { TeamEditComponent } from '../team-edit/team-edit.component';
import { TeamViewComponent } from '../team-view/team-view.component';
import { TeamDeleteComponent } from '../team-delete/team-delete.component';
import { ToastrService } from 'ngx-toastr';
import { MatTabGroup } from '@angular/material/tabs';

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

  newTeamName?: string = '';
  teamNameFormControl = new FormControl('', [Validators.required]);
  newTeamLeaderId?: string = '';
  teamLeaderFormControl = new FormControl();
  users?: UserView[];
  errorMessage?: string;

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;
  // @ts-ignore
  @ViewChild('tabs') tabGroup: MatTabGroup;

  constructor(private teamService: TeamsService, 
              public dialog: MatDialog, 
              private userService: UserService,
              private route: ActivatedRoute,
              private router: Router,
              private toastr: ToastrService){
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
          this.toastr.success('new team created !', '', {
            timeOut: 2000,
            progressBar: true
          });
          // @ts-ignore
          this.tabGroup.selectedIndex = this.tabGroup.selectedIndex-1;
         } else {
          this.toastr.error('something went wrong ..','', {
            timeOut: 2000,
            progressBar: true
          });
         }})
    }
  }

  onView(id: string) {
    this.dialog.open( TeamViewComponent, {data: id} );
  }

  edit(id: string) {
    const dialogRef = this.dialog.open( TeamEditComponent, {data: id});
    dialogRef.afterClosed().subscribe(result => {
      this.reloadTeams();
    });
  }

  delete(id: string) {
    const dialogRef = this.dialog.open( TeamDeleteComponent, {data: id} );
    dialogRef.afterClosed().subscribe(result => {
      this.reloadTeams();
    });
  }
}
