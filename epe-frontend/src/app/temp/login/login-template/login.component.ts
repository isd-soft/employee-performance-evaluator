import { Router } from '@angular/router';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { LoginService } from './../login-service/login.service';
import { UserLoginRequest } from '../login-models/user-login-request.interface';
import { UserLoginResponse } from '../login-models/user-login-response.interface';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';

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

  constructor(private loginService: LoginService,
              private jwtService: JwtService,
              private router: Router) { 
    this.createNewEmptyUser()
    this.jwtService.getJwtUser()
  }

  ngOnInit() {
    if(localStorage.getItem('JWT_TOKEN')) {
      this.loggedUser = this.jwtService.getJwtUser()
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
          this.router.navigate(['/dashboard'])
        }
       }, error => { 
        console.log(error) 
        this.errorMsg = error })
    }
  }
}
