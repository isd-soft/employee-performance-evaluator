import { MyProfile } from './../profile-models/my-profile.interface';
import { ProfileService } from './../profile-service/profile.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  myProfile?: MyProfile;

  constructor(private profileService: ProfileService) { 
    this.profileService.getMyProfile().subscribe(data => {
      this.myProfile = data as MyProfile;
    });
  }

  ngOnInit(): void {
    this.profileService.getMyProfile().subscribe(data => {
      this.myProfile = data as MyProfile;
    });
  }

}
