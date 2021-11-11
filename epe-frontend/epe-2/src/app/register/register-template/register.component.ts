import { Component, OnInit } from '@angular/core';
import { Job } from '../register-models/job';
import { NewUser } from '../register-models/new-user.interface';
import { RegisterService } from '../register-service/register.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  newUser?: NewUser
  jobs?: Job[]

  constructor(private registerService: RegisterService) {
    this.createNewEmptyUser()
   }

  ngOnInit(): void {
    this.jobs = this.registerService.getJobs()
    console.log(this.jobs)
  }

  createNewEmptyUser() {
    this.newUser = {
      firstname: '',
      lastname: '',
      birthDate: '',
      email: '',
      phone: '',
      job: '',
      employmentDate: '',
      password: '',
      confirm_pasword: ''
    }
  }

  registerUser() {
    console.log(this.newUser)
  }

}
