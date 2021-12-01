import { Router } from '@angular/router';
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

  refreshed = false;
  myProfile?: MyProfile;
  myTeams?: TeamView[];
  contor = 0;

  constructor(private profileService: ProfileService,
              private router: Router,
              private dialog: MatDialog) {
    
    this.refreshAll();

    // if(!localStorage.getItem('MY_PROFILE_RELOADED')) {
    //   window.location.reload();
    //   localStorage.setItem('MY_PROFILE_RELOADED', 'true');
    // }

  }

  reload() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/my-profile']);
  }

  refreshAll() {
    this.refreshed = true;
    this.profileService.getMyProfile().subscribe(data => {
      this.myProfile = data as MyProfile;
    });

    this.profileService.getMyTeams().subscribe(data => {
      this.myTeams = data as TeamView[];
    });
  }

  ngOnInit(): void {
    this.refreshAll();
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
