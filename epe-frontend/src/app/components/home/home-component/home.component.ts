import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';
import { LoginResponse } from './../home-models/login-response.interface';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/temp/register/register-models/register-request.interface';
import { LoginRequest } from '../home-models/login-request.interface';
import { HomeFiller } from './home.filler';
import { HomeService } from '../home-service/home.service';
import { BadResponse } from './../home-models/bad-response.interface';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  hasAccount = true;

  loginUser?: LoginRequest;
  registerUser?: RegisterRequest;

  errorMessage?: string;

  constructor(private jwtService: JwtService,
              private homeService: HomeService,
              private filler: HomeFiller,
              private router: Router) {

    this.loginUser = this.filler.createEmptyLoginUser();
    this.registerUser = this.filler.createEmptyRegisterUser();
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
         console.log(error)
        this.errorMessage = "Bad credentials !" })
    }
  }

}
