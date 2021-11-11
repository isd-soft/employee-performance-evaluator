import { LoginService } from './../login-service/login.service';
import { Component } from '@angular/core';
import { UserLoginRequest } from '../login-models/user-login-request.interface';
import { UserLoginResponse } from '../login-models/user-login-response.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  imgPath = '../../../assets/login-images/'
  user?: UserLoginRequest
  response?: UserLoginResponse
  errorMsg?: any

  constructor(private loginService: LoginService) { 
    this.createNewEmptyUser()
  }

  createNewEmptyUser() {
    this.user = {
      email: '',
      password: ''
    }
  }

  emptyResponseAndErrorMsg() {
    this.response = undefined
    this.errorMsg = undefined
  }

  login() {
    if(this.user) {
      this.emptyResponseAndErrorMsg()
      this.loginService.login(this.user).subscribe(data => {
        this.response = data as UserLoginResponse
       }, error => { 
        console.log(error) 
        this.errorMsg = error })
    }
  }
}
