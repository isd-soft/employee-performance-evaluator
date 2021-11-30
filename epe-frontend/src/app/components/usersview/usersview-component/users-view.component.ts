import { Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {UserviewsServices} from "../userview-services/users-view.service";
import {User} from "../userview-models/User";
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from "@angular/material/dialog";
import {UserComponent} from "../../user/user-component/user.component";
import {RoleChangeComponent} from "../../../role-change/role-change-component/role-change.component";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {AssessmentTemplate} from "../userview-models/Assessment-template"
import {AssessmentComponent} from "../../assessment/assessment-component/assessment.component";
import {FormBuilder, FormGroup} from "@angular/forms";
import {NewUser} from "../userview-models/NewUser";

import {RoleService} from "../../../role-change/role-change-service/role.service";
import {JobItem} from "../../home/home-models/job-item.interface";
import {CreateUserService} from "../userview-services/create-user.service";
import {DatePipe} from "@angular/common";
import { UserDeleteComponent } from '../user-delete/user-delete.component';
import {TeamView} from "../../teams/teams-model/team-view.interface";
import {ShortUser} from "../../teams/teams-model/short-user.interface";
import {Router} from "@angular/router";
import {CreateUserComponent} from "../../../create-user/create-component/create-user.component";

// @ts-ignore
import {saveAs} from 'file-saver/dist/FileSaver';

@Component({
  selector: 'app-usersview',
  templateUrl: './users-view.component.html',
  styleUrls: ['./users-view.component.css'],
  encapsulation: ViewEncapsulation.None
})

export class UsersView {

  displayedColumns: string[] = ['firstname', 'lastname', 'email', 'job','assessment_status', 'buttons'];
  // @ts-ignore
  dataSource: MatTableDataSource<User>;
  // @ts-ignore
  dataSource2: MatTableDataSource<ShortUser>;
  users!: User[];
  jwtUser?: JwtUser;
  assessmentTemplates? : AssessmentTemplate[];
  assignedUsers?: User[]
  role?: string;
  id?: string;
  requiredRole : string = "ROLE_SYSADMIN";

  newUser?: FormGroup;

  userDto?: NewUser;

  teamLeader?: NewUser;

  userToExport?: NewUser;

  jobList?: JobItem[];

  teamList?: TeamView[];

  teamMembers?: ShortUser[];

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  // @ts-ignore
  @ViewChild(MatPaginator) paginator2: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort2: MatSort;

  constructor(private userviewsServices: UserviewsServices,
              public dialog: MatDialog,
              private jwtService: JwtService,
              private roleService: RoleService,
              private formBuilder: FormBuilder,
              private createService: CreateUserService,
              private router: Router){
    this.jwtUser = this.jwtService.getJwtUser();
    if (this.jwtUser) {
      this.role = this.jwtUser.role;
      this.id = this.jwtUser.id;
    }


    // @ts-ignore
    this.userDto = {
      email: '',
      firstname: '',
      lastname: '',
      birthDate: '',
      job : '',
      employmentDate: '',
      phoneNumber: '',
      password: '1234321',
      bio: 'new user'
    }

    this.userviewsServices.getTeamLeader().subscribe(data =>
      this.teamLeader = data as NewUser);

  }

  getTeamMembers() {
    this.userviewsServices.getTeamMembers().subscribe(data => {
      this.teamMembers = data as ShortUser[]
      this.dataSource2 = new MatTableDataSource(this.teamMembers);
      this.dataSource2.paginator = this.paginator2;
      this.dataSource2.sort = this.sort2;
    });



  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.dataSource2.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }

    // if (this.dataSource2.paginator) {
    //   this.dataSource2.paginator.firstPage();
    // }
  }

  refreshAllResources() {
    this.getUsers();
    this.getRoles();
    this.getAssignedUsers();
    this.getTeamMembers();
  }

  getUsers() {
    this.userviewsServices.getUserList().subscribe(data => {
      this.users = data as User[];
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  getRoles() {
    this.roleService.getJobList().subscribe(data =>
      this.jobList = data as JobItem[]
    );
  }

  getAssignedUsers() {
    this.userviewsServices.getAssignedUsers().subscribe(data =>{
      this.assignedUsers = data as User[];
    })
  }


  ngOnInit(){

    this.refreshAllResources();

    this.newUser = this.formBuilder.group({
      email: [],
      firstname: [],
      lastname: [],
      birthDate: [],
      employmentDate: [],
      phoneNumber: [],
      job: [],
      password: [this.userDto?.password],
      bio: [this.userDto?.bio]
    })

  }

  onView(user : string) {
    // console.log(this.teamMembers)
    this.dialog.open( UserComponent, {data: user} );
  }

  edit(userId : number) {
    this.dialog.open(RoleChangeComponent, {width: '45%', data : this.users[userId]});
    this.refreshAllResources();
  }

  delete(id: string) {
    const dialogRef = this.dialog.open( UserDeleteComponent, {data: id} );
    dialogRef.afterClosed().subscribe(result => {
      this.refreshAllResources();
    });
  }

  startAssessment(id: string) {
    this.userviewsServices.getAssessmentTemplates().subscribe(assessmentTemplate => {
      this.assessmentTemplates = assessmentTemplate as AssessmentTemplate[]
    })
    this.dialog.open(AssessmentComponent, {data: id})

    // this.dialog.open(AssessmentComponent, {data: id,});

    // this.dialog.closeAll();
  }

  containsUserId(userId: string){
    let flag = false;
    this.assignedUsers?.forEach(user => {
      if (user.id === userId){
        flag = true;
      }
    })
    return flag || false;
  }

  createUser() {
    let datePipe = new DatePipe('en-US');
    // @ts-ignore
    this.newUser?.value.birthDate = datePipe.transform(this.newUser?.value.birthDate, 'dd-MM-yyyy') as string;
    // @ts-ignore
    this.newUser?.value.employmentDate = datePipe.transform(this.newUser?.value.employmentDate, 'dd-MM-yyyy') as string;
    this.createService.createUser(this.newUser?.value);
    this.refreshAllResources();
  }

  createNewUser() {
    this.dialog.open(CreateUserComponent, {width: '50%'});
  }

  exportToPdf(user : NewUser | undefined) {
    this.userviewsServices.exportToPdf(user);
  }

  exportToExcel(user : NewUser | undefined) {
    this.userviewsServices.exportToExcel(user);
  }

  exportAllToPdf() {
    this.userviewsServices.exportAllToPdf();
  }

  exportAllToExcel() {
    this.userviewsServices.exportAllToExcel();
  }
}
