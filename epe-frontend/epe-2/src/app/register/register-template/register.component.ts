import { Component, OnInit } from '@angular/core';
import { NewUser } from '../register-models/new-user.interface';
import { RegisterService } from './../register-service/register.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  newUser?: NewUser

  // private registerService: RegisterService

  constructor() {
    this.createNewEmptyUser()
   }

  ngOnInit(): void {
  }

  createNewEmptyUser() {
    this.newUser = {
      firstname: '',
      lastname: '',
      email: '',
      phone: '',
      password: '',
      confirm_pasword: ''
    }
  }

  registerUser() {
    console.log(this.newUser)
  }

}
