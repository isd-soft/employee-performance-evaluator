import {Component, Inject, OnInit} from '@angular/core';
import {Role} from "../role-change-model/role.interface";
import {RoleService} from "../role-change-service/role.service";
import {JwtService} from "../../decoder/decoder-service/jwt.service";
import {Router} from "@angular/router";
import {Observable, ReplaySubject} from "rxjs";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {JobItem} from "../../components/edit/edit-models/job-item.interface";
import {User} from "../../components/edit/edit-models/user.interface";
import {FormBuilder, FormGroup} from "@angular/forms";
import {JwtUser} from "../../decoder/decoder-model/jwt-user.interface";

@Component({
  selector: 'app-role-change',
  templateUrl: './role-change.component.html',
  styleUrls: ['./role-change.component.css']
})
export class RoleChangeComponent implements OnInit {

  user!: User
  buddies!: User[]

  auxUser!: FormGroup;

  jobList?: JobItem[];

  errorMessage? : string;

  roles?: string[];
  role? : string;

  requiredRole: string = "ROLE_SYSADMIN";
  currentRole?: string;

  currentUserId?: string

  jwtUser?: JwtUser;


  constructor(
    @Inject(MAT_DIALOG_DATA) public data : any,
    private jwtService: JwtService,
              private roleService: RoleService,
              private formBuilder: FormBuilder) {

    this.user = data as User;
    this.auxUser = this.formBuilder.group({
      email: [this.user?.email],
      firstname: [this.user?.firstname],
      lastname: [this.user?.lastname],
      birthDate: [this.user?.birthDate],
      phoneNumber: [this.user?.phoneNumber],
      image: [this.base64Output],
      job: [this.user?.job],
      employmentDate: [this.user?.employmentDate],
      bio: [this.user?.bio],
      role: [this.user?.role]
    });

    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser) {
      this.currentUserId = this.jwtUser.id;
      this.currentRole = this.jwtUser.role;
    }

    this.roleService.getJobList().subscribe(data =>
      this.jobList = data as JobItem[]);
    this.role = this.roleService.getRole();
    // @ts-ignore
    this.roleService.getRoles().subscribe(data => {
      this.roles = data as string[]});
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
    this.auxUser?.value.image = this.base64Output;
    this.roleService.updateUser(this.auxUser?.value, this.user.id, this.currentUserId);
  }

  ngOnInit(): void {
  }

}
