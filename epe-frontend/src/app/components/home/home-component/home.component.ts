import { JobItem } from './../home-models/job-item.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';
import { LoginResponse } from './../home-models/login-response.interface';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from '../home-models/login-request.interface';
import { HomeFiller } from './home.filler';
import { HomeService } from '../home-service/home.service';
import { NewUser } from '../home-models/new-user.interface';
import { RegisterRequest } from '../home-models/register-request.interface';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  hasAccount = true;

  loginUser?: LoginRequest;
  newUser?: NewUser;

  jobList?: JobItem[]

  errorMessage?: string;

  constructor(private jwtService: JwtService,
              private homeService: HomeService,
              private filler: HomeFiller,
              private router: Router) {

    this.loginUser = this.filler.createEmptyLoginUser();
    this.newUser = this.filler.createEmptyNewUser();
    this.jobList = this.homeService.getJobList();
   }

   requestRegisterPage() {
    this.hasAccount = false;
   }

   requestLoginPage() {
    this.hasAccount = true;
   }

   login() {

    if(this.loginUser) {
      this.homeService.login(this.loginUser).subscribe(data => {
        let response = data as LoginResponse;
        if(response.token) {
          this.jwtService.storeJWT(response.token);
          this.router.navigate(['/dashboard']);
        }
       }, error => {
        if(error == 500) {
          this.errorMessage = 'Email or password is incorrect !'; }
        else {
          this.errorMessage = 'Something went wrong, please try again in a few minutes ...';
        } })
    }
  }

  register() {
    if(this.newUser)
      if(this.newUser.password == this.newUser.confirmPassword) {
        let registerUser: RegisterRequest = this.filler.createRegisterUserFromNewUser(this.newUser);
        this.homeService.register(registerUser).subscribe(data => {}, error => {
          if(error == 200) {
            this.hasAccount = true;
            this.errorMessage = undefined;
            this.newUser = this.filler.createEmptyNewUser();
            this.router.navigate([''])
          } else if(error == 400) {
            this.errorMessage = 'An user with such email already exists !'; }
          else {
            this.errorMessage = 'Something went wrong, please try again in a few minutes ...';
          } })
      } else {
        this.errorMessage = 'The passwords do not match !';
      }
  }

}
