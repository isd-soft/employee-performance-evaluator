import {AfterViewInit, Component, ViewChild, ViewEncapsulation} from '@angular/core';
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
import { Router } from '@angular/router';
import {AssessmentTemplate} from "../userview-models/Assessment-template"
import {AssessmentComponent} from "../../assessment/assessment-component/assessment.component";
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-usersview',
  templateUrl: './users-view.component.html',
  styleUrls: ['./users-view.component.css'],
  encapsulation: ViewEncapsulation.None
})

export class UsersView implements AfterViewInit {
  displayedColumns: string[] = ['firstname', 'lastname', 'email', 'job','assessment_status', 'buttons'];
  // @ts-ignore
  dataSource: MatTableDataSource<User>;
  users!: User[];
  jwtUser?: JwtUser;
  assessmentTemplates? : AssessmentTemplate[];
  assignedUsers?: User[]
  role?: string;
  requiredRole : string = "ROLE_SYSADMIN";

  newUser?: FormGroup;


  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;



  constructor(private userviewsServices: UserviewsServices, public dialog: MatDialog, private jwtService: JwtService){
    this.jwtUser = jwtService.getJwtUser();
    this.role = this.userviewsServices.getRole();

  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngAfterViewInit() {
    // this.reloadData();
  }

  ngOnInit(){

    this.userviewsServices.getAssignedUsers().subscribe(data =>{
      this.assignedUsers = data as User[];
    })

    this.userviewsServices.getUserList().subscribe(data => {
      this.users = data as User[];
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });


  }

  onView(user : string) {
    this.dialog.open( UserComponent, {data: user} );
  }

  edit(userId : number) {
    this.dialog.open(RoleChangeComponent, {width: '45%', data : this.users[userId]});
    this.dialog.afterAllClosed;
  }

  delete(user : string) {
    this.userviewsServices.deleteUser(user);
    this.reloadComponent();
  }
  reloadComponent() {
    // @ts-ignore
    let currentUrl = this.router.url;
    // @ts-ignore
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    // @ts-ignore
    this.router.onSameUrlNavigation = 'reload';
    // @ts-ignore
    this.router.navigate([currentUrl]);
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
}
