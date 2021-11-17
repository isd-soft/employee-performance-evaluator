import { Component, OnInit } from '@angular/core';
import {UserviewsServices} from "../userview-services/users-view.service";
import {User} from "../userview-models/User";

@Component({
  selector: 'app-usersview',
  templateUrl: './users-view.component.html',
  styleUrls: ['./users-view.component.css']
})
export class UsersView implements OnInit {
  users?: User[]

  constructor(private userview: UserviewsServices) {
     this.userview.getUserList().subscribe(data => {
      this.users = data as User[]
    })

    console.log(this.users)
  }

  ngOnInit(): void {
  }

}
