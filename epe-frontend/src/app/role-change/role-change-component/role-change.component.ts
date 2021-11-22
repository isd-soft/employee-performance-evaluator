import { Component, OnInit } from '@angular/core';
import {Role} from "../role-change-model/role.interface";
import {RoleService} from "../role-change-service/role.service";

@Component({
  selector: 'app-role-change',
  templateUrl: './role-change.component.html',
  styleUrls: ['./role-change.component.css']
})
export class RoleChangeComponent implements OnInit {

  roleDto? : Role;

  rolesList?: string[];


  constructor(private roleService?: RoleService) {
    this.roleService?.getRoles().subscribe(data =>
      this.rolesList = data as string[]);
  }

  ngOnInit(): void {
  }

}
