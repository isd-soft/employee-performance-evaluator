import { RegisterRequest } from './../register-models/register-request.interface';
import { Component, OnInit } from '@angular/core';
import { Job } from '../register-models/job';
import { NewUser } from '../register-models/new-user.interface';
import { RegisterService } from '../register-service/register.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  newUser?: NewUser
  newRegister?: RegisterRequest
  jobs?: Job[]

  constructor(private registerService: RegisterService,
              private router: Router,
    ) {
    this.createNewEmptyUser()
   }

  ngOnInit(): void {
    console.log('register')
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
      bio: 'null',
    }
  }

  registerUser() {
    if(this.newUser) {
      console.log(this.newUser)
      this.fillRegisterRequestBody(this.newUser)
      if(this.newRegister) {
        console.log(this.newRegister)
        this.registerService.register(this.newRegister).subscribe(data => {
          this.router.navigate(['/login'])
        }, error => { 
          console.log(error) })
      }
    }
  }

}
