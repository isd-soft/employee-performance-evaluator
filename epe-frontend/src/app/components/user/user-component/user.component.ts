import {Component, Inject, OnInit} from '@angular/core';
import {User} from "../user-model/User";
import {UserService} from "../user-service/user.service.js";
import {Assessment} from "../user-model/Assessment";
import {JobItem} from "../../home/home-models/job-item.interface";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user: User | undefined;
  id: string | undefined;
  assessments: Assessment[] | undefined

  constructor(private userService: UserService) {
    this.userService.getUser().subscribe(userInfo => {
      this.user = userInfo as User;
    });
    this.userService.getAssessment().subscribe(assessment => {
      this.assessments = assessment as Assessment[];
      console.log(assessment)
    })
  }

  ngOnInit(): void {

  }

}
