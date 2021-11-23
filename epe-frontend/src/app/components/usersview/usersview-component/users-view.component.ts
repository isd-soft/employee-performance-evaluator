import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {UserviewsServices} from "../userview-services/users-view.service";
import {User} from "../userview-models/User";
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from "@angular/material/dialog";
import {UserComponent} from "../../user/user-component/user.component";
import {UserService} from "../../user/user-service/user.service.js";
import {HomeComponent} from "../../home/home-component/home.component";
import {identity} from "rxjs";
import {RoleChangeComponent} from "../../../role-change/role-change-component/role-change.component";
import {EditComponent} from "../../../edit/edit-component/edit.component";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";

@Component({
  selector: 'app-usersview',
  templateUrl: './users-view.component.html',
  styleUrls: ['./users-view.component.css']
})

export class UsersView implements AfterViewInit {
  displayedColumns: string[] = ['firstname', 'lastname', 'email', 'job', 'buttons'];
  // @ts-ignore
  dataSource: MatTableDataSource<User>;
  users?: User[];
  jwtUser?: JwtUser;

  jwtUserId? : string;

  jwtUserRole? : string;

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;


  role?: string;
  requiredRole : string = "ROLE_SYSADMIN";

  constructor(private userview: UserviewsServices, public dialog: MatDialog, private jwtService: JwtService){
    this.jwtUser = jwtService.getJwtUser();
    // @ts-ignore
    console.log(this.jwtUser.id);
    this.jwtUserId = this.jwtUser?.id;
    this.role = this.userview.getRole();
    console.log(this.role);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngAfterViewInit() {
    this.userview.getUserList().subscribe(data => {
      this.users = data as User[];
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });

  }

  onView(user : string) {
    this.dialog.open( UserComponent, {data: user} );
  }

  edit(user : string) {
    this.dialog.open(RoleChangeComponent, {height:'100%',width:'70%', data : user});
    // this.dialog.afterAllClosed.
  }

  delete(user : string) {
    this.userview.deleteUser(user);
  }
}
