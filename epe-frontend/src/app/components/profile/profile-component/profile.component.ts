import { MyProfile } from './../profile-models/my-profile.interface';
import { ProfileService } from './../profile-service/profile.service';
import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {PasswordComponent} from "../../password/password-component/password.component";
import {RoleChangeComponent} from "../../../role-change/role-change-component/role-change.component";
import { TeamView } from '../../teams/teams-model/team-view.interface';
import { TeamViewComponent } from '../../teams/team-view/team-view.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  myProfile?: MyProfile;
  myTeams?: TeamView[];

  constructor(private profileService: ProfileService,
              private dialog: MatDialog) {
    this.profileService.getMyProfile().subscribe(data => {
      this.myProfile = data as MyProfile;
    });

    this.profileService.getMyTeams().subscribe(data => {
      this.myTeams = data as TeamView[];
    });
  }

  ngOnInit(): void {
    this.profileService.getMyProfile().subscribe(data => {
      this.myProfile = data as MyProfile;
    });
  }

  edit() {
    this.dialog.open(RoleChangeComponent,{width:'40%', data:this.myProfile})
  }

  changePassword() {
    this.dialog.open(PasswordComponent)
  }

  viewTeam(id: string) {
    this.dialog.open( TeamViewComponent, {data: id} );
  }
}
