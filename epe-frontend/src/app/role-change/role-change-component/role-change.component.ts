import {Component, Inject, OnInit} from '@angular/core';
import {Role} from "../role-change-model/role.interface";
import {RoleService} from "../role-change-service/role.service";
import {JwtService} from "../../decoder/decoder-service/jwt.service";
import {Router} from "@angular/router";
import {Observable, ReplaySubject} from "rxjs";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {JobItem} from "../../components/edit/edit-models/job-item.interface";
import {User} from "../../components/edit/edit-models/user.interface";

@Component({
  selector: 'app-role-change',
  templateUrl: './role-change.component.html',
  styleUrls: ['./role-change.component.css']
})
export class RoleChangeComponent implements OnInit {

  user?: User

  jobList?: JobItem[];

  errorMessage? : string;

  roles?: string[];
  role? : string;
  requiredRole : string = "ROLE_SYSADMIN";

  constructor(
    @Inject(MAT_DIALOG_DATA) public userId : any,
    private jwtService: JwtService, private roleService: RoleService) {
    this.roleService.getUserData(userId).subscribe(data =>
      this.user = data as User);
    this.roleService.getJobList().subscribe(data =>
      this.jobList = data as JobItem[]);
    this.role = this.roleService.getRole();
    this.roleService.getRoles().subscribe(data => {
      this.roles = data as string[]});
    // console.log(this.userId);
  }

  url = "";

  base64Output? : string;

  // @ts-ignore
  selectFile(event) {
    if (event.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (event : any) => {
        this.url = event.target.result;
      }
      this.convertFile(event.target.files[0]).subscribe(base64 => {
        this.base64Output = base64;
      });
    }
  }

  convertFile(file : File) : Observable<string> {
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    // @ts-ignore
    reader.onload = (event) => result.next(btoa(event.target.result.toString()));
    return result;
  }




  update() {
    // @ts-ignore
    this.user.image = this.base64Output;
    this.roleService.updateUser(this.user,this.userId).subscribe(data => {
    }, error => {
      this.errorMessage = error.error.title;})

  }

  ngOnInit(): void {
  }

}
