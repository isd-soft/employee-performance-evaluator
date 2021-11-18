import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { UserView } from '../user-models/user-view.interface';
import { UsersService } from '../user-service/users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {

  userList?: UserView[];

  constructor(private usersService: UsersService,
              private router: Router) { 
    this.refreshUsers();
  }

  refreshUsers() {
    this.usersService.getUsers().subscribe(data => {
      this.userList = data as UserView[];
    });
  }

  createNewUser() {
    this.router.navigate(['/user-create']);
  }

   editUser(id: string) {
     localStorage.setItem("USER_ID", id);
     this.router.navigate(['/user-edit']);
  }

}
