import { JwtService } from './../../decoder/decoder-service/jwt.service';
import { JwtUser } from './../../decoder/decoder-model/jwt-user.interface';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { LoginService } from './../login-service/login.service';
import { UserLoginRequest } from '../login-models/user-login-request.interface';
import { UserLoginResponse } from '../login-models/user-login-response.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  // get 
  loggedUser?: JwtUser

  // util, show warning, error images
  imgPath = '../../../assets/login-images/'
  
  // request and response models
  user?: UserLoginRequest
  response?: UserLoginResponse
  
  // error message in case login failed
  errorMsg?: any

  // test, working with jwt
  show?: any

  constructor(private loginService: LoginService,
              private jwtService: JwtService) { 
    this.createNewEmptyUser()
  }

  ngOnInit() {
    if(localStorage.getItem('JWT_TOKEN')) {
      this.loggedUser = this.jwtService.decodeJwt()
      console.log(this.loggedUser)
    }
  }

  ngOnDestroy() {
    this.loggedUser = undefined
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
          this.jwtService.storeJWT(this.response.token)
        }
       }, error => { 
        console.log(error) 
        this.errorMsg = error })
    }
  }
}
