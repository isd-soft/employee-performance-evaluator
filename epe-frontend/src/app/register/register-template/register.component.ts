import { RegisterRequest } from './../register-models/register-request.interface';
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
  newRegister?: RegisterRequest
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

  createNewEmptyRegisterRequestBody() {
    this.newRegister = {
      email: '',
      password: '',
      firstname: '',
      lastname: '',
      birthDate: '',
      employmentDate: '',
      phoneNumber: '',
      job: '',
      bio: '',
    }
  }

  fillRegisterRequestBody(user: NewUser) {
    this.newRegister = {
      email: user.email,
      password: user.password,
      firstname: user.firstname,
      lastname: user.lastname,
      birthDate: user.birthDate,
      employmentDate: user.employmentDate,
      phoneNumber: user.phone,
      job: user.job,
      bio: '',
    }
  }

  registerUser() {
    if(this.newUser) {
      this.fillRegisterRequestBody(this.newUser)
      console.log(this.newRegister)
    }
  }

}
