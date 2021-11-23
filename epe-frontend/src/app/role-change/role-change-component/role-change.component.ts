import { Component, OnInit } from '@angular/core';
import {Role} from "../role-change-model/role.interface";
import {RoleService} from "../role-change-service/role.service";
import {User} from "../../edit/edit-models/user.interface";
import {JobItem} from "../../edit/edit-models/job-item.interface";
import {JwtService} from "../../decoder/decoder-service/jwt.service";
import {EditService} from "../../edit/edit-service/edit.service";
import {EditFiller} from "../../edit/edit-component/edit.filler";
import {Router} from "@angular/router";
import {Observable, ReplaySubject} from "rxjs";

@Component({
  selector: 'app-role-change',
  templateUrl: './role-change.component.html',
  styleUrls: ['./role-change.component.css']
})
export class RoleChangeComponent implements OnInit {

  user?: User

  jobList?: JobItem[];

  auxUser?: User;

  errorMessage? : string;

  roles?: string[];
  role? : string;
  requiredRole : string = "ROLE_SYSADMIN";

  constructor(private jwtService: JwtService,
              private editService: EditService,
              private filler: EditFiller,
              private router: Router) {
    this.editService.getUser().subscribe(data =>
      this.auxUser = data as User);
    this.editService.getJobList().subscribe(data =>
      this.jobList = data as JobItem[]);
    this.role = this.editService.getRole();
    this.editService.getRoles().subscribe(data => {
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
    this.auxUser.image = this.base64Output;
    this.editService.update(this.auxUser).subscribe(data => {
    }, error => {
      this.errorMessage = error.error.title;})

  }

  ngOnInit(): void {
  }

}
