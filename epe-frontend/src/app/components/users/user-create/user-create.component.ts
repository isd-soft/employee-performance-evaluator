import { Router } from '@angular/router';
import { UsersService } from './../user-service/users.service';
import { Component } from '@angular/core';
import { JobItem } from '../../home/home-models/job-item.interface';
import { CreateUserRequest } from '../user-models/create-user.interface';
import { RegisterRequest } from '../../home/home-models/register-request.interface';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent  {

  newUser?: CreateUserRequest;

  jobList?: JobItem[];
  errorMessage?: string;

  constructor(private usersService: UsersService,
              private router: Router) { 

    this.newUser = this.createEmptyUser();
    this.usersService.getJobs().subscribe(data => {
      this.jobList = data as JobItem[];
    });
  }

  createUser() {
    if(this.newUser) {
      let datePipe = new DatePipe('en-US');
      let registerUser: RegisterRequest = {
        email: this.newUser.email,
        firstname: this.newUser.firstname,
        lastname: this.newUser.lastname,
        birthDate: datePipe.transform(this.newUser.birthDate, 'dd-MM-yyyy') as string,
        employmentDate: datePipe.transform(this.newUser.employmentDate, 'dd-MM-yyyy') as string,
        phoneNumber: this.newUser.phoneNumber,
        job: this.newUser.job,
        bio: 'new user created by admin',
        password: 'password'
      }

      this.usersService.createUser(registerUser).subscribe(data => {
        this.router.navigate(['/users']);
       }, error => {
            this.errorMessage = error.error.title;})
  }
}

createEmptyUser(): CreateUserRequest {
  let temp: CreateUserRequest = {
    email: '',
    firstname: '',
    lastname: '',
    birthDate: '',
    employmentDate: '',
    phoneNumber: '',
    job: '',
    bio: '',
    password: ''
  }
  
  return temp;
}

}
