import {Component, Inject, OnInit} from '@angular/core';
import {User} from "../user-model/User";
import {UserService} from "../user-service/user.service.js";
import {Assessment} from "../user-model/Assessment";
import {JobItem} from "../../home/home-models/job-item.interface";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  // @ts-ignore
  user: User;
  // @ts-ignore
  assessments: Assessment[] ;

  constructor(
    @Inject(MAT_DIALOG_DATA) public userId: any, private userService: UserService) {
    // @ts-ignore
    this.userService.getUser(userId).subscribe(userInfo => {
      this.user = userInfo as User;
    });

    // @ts-ignore
    this.userService.getAssessment(userId).subscribe(assessment => {
      this.assessments = assessment as Assessment[];
      // console.log(assessment);
    })
  }

  ngOnInit(): void {

  }

}
