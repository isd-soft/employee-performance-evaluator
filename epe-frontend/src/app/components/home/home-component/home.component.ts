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
    this.homeService.getJobList().subscribe(data => {
      this.jobList = data as JobItem[];
    })
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
        this.errorMessage = error.error.title;})
    }
  }

  register() {
    if(this.newUser)
      if(this.newUser.password == this.newUser.confirmPassword) {
        let registerUser: RegisterRequest = this.filler.createRegisterUserFromNewUser(this.newUser);
        this.homeService.register(registerUser).subscribe(data => {
          this.hasAccount = true;
          this.errorMessage = undefined;
          this.newUser = this.filler.createEmptyNewUser();
          this.router.navigate(['/home']);
         }, error => {
            this.errorMessage = error.error.title;})
      } else {
        this.errorMessage = 'The passwords do not match !';
      }
  }

}
