import {Component, Inject, OnInit} from '@angular/core';
import {User} from "../user-model/User";
import {UserService} from "../user-service/user.service.js";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user: User | undefined;
  id: string | undefined;

  constructor(private userService: UserService) {
    this.userService.getUser().subscribe(data => {
      this.user = data as User;
    });
  }

  ngOnInit(): void {

  }

}
