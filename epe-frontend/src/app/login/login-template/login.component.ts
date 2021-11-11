import { Component } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { LoginService } from './../login-service/login.service';
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
  show?: any

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
        if(this.response.token) {
          this.show = this.getDecodedAccessToken(this.response.token)
          console.log(this.show)
        }
       }, error => { 
        console.log(error) 
        this.errorMsg = error })
    }
  }

  getDecodedAccessToken(token: string): any {
    try{
        return jwt_decode(token);
    }
    catch(Error){
        return null;
    }
  }
}
