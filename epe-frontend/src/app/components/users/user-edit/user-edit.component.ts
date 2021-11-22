import { UserView } from './../user-models/user-view.interface';
import { Router } from '@angular/router';
import { UsersService } from './../user-service/users.service';
import { Component } from '@angular/core';
import { JobItem } from '../../home/home-models/job-item.interface';
import { CreateUserRequest } from '../user-models/create-user.interface';
import { RegisterRequest } from '../../home/home-models/register-request.interface';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent {

  userID: string;
  editUser?: CreateUserRequest;
  getUser?: UserView;

  jobList?: JobItem[];
  errorMessage?: string;

  constructor(private usersService: UsersService,
              private router: Router) { 

    this.userID = localStorage.getItem("USER_ID");
    localStorage.removeItem("USER_ID");

    this.usersService.getUser(this.userID).subscribe(data => {
      this.getUser = data as UserView;
      this.editUser = this.createEditUserFromOriginalUser();
    });

    this.usersService.getJobs().subscribe(data => {
      this.jobList = data as JobItem[];
    });
  }

  log() {
      console.log(this.getUser);
  }

  updateUser() {
    if(this.editUser) {
      let datePipe = new DatePipe('en-US');
      let registerUser: RegisterRequest = {
        email: this.editUser.email,
        firstname: this.editUser.firstname,
        lastname: this.editUser.lastname,
        birthDate: this.editUser.birthDate,
        employmentDate: this.editUser.employmentDate,
        phoneNumber: this.editUser.phoneNumber,
        job: this.editUser.job,
        bio: 'user edited by admin',
        password: 'password'
      }

      this.usersService.updateUser(this.userID, registerUser).subscribe(data => {
        this.router.navigate(['/users']);
       }, error => {
            this.errorMessage = error.error.title;})
  }
}

createEditUserFromOriginalUser(): CreateUserRequest {
  
  let temp: CreateUserRequest = {
    email: this.getUser.email, 
    firstname: this.getUser.firstname,
    lastname: this.getUser.lastname,
    birthDate: this.getUser.birthDate, 
    employmentDate: this.getUser.employmentDate,
    phoneNumber: this.getUser.phoneNumber,
    job: this.getUser.job,
    bio: this.getUser.bio,
    password: ''
  }
  
  return temp;
}

}
