import { MyProfile } from './../profile-models/my-profile.interface';
import { ProfileService } from './../profile-service/profile.service';
import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {EditComponent} from "../../edit/edit-component/edit.component";
import {PasswordComponent} from "../../password/password-component/password.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  myProfile?: MyProfile;

  constructor(private profileService: ProfileService,
              private dialog: MatDialog) {
    this.profileService.getMyProfile().subscribe(data => {
      this.myProfile = data as MyProfile;
    });
  }

  ngOnInit(): void {
    this.profileService.getMyProfile().subscribe(data => {
      this.myProfile = data as MyProfile;
    });
  }

  edit() {
    this.dialog.open(EditComponent,{height:'100%'})
  }

  changePassword() {
    this.dialog.open(PasswordComponent)
  }
}
