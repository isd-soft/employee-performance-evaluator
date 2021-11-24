import { AuthenticationService } from '../../../services/auth/authentication.service';
import { Component } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/jwt/jwt-service.service';
import { LoginRequest } from 'src/app/models/login-request.model';
import { LoginResponse } from 'src/app/models/login-response.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginUser: LoginRequest = {
    email: '',
    password: ''
  };

  errorMessage?: string;

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordFormControl = new FormControl('', [Validators.required]);

  constructor(private router: Router,
    private jwtService: JwtService,
    private authService: AuthenticationService,
    private toastr: ToastrService) {
  }

  login() {
    if(this.loginUser) {
      this.authService.login(this.loginUser).subscribe(data => {
        let response = data as LoginResponse;
        if(response.token) {
          this.jwtService.saveJWT(response.token);
          this.toastr.success('Login succesful ! </br> You will be redirected to dashboard', '', {
            timeOut: 1000,
            progressBar: true,
            enableHtml: true
          });
          setTimeout(()=> {
            this.router.navigate(['/dashboard']);
           }, 2000);
        }
       }, error => {
         this.toastr.error('Something went wrong .. </br> Please check your credentials','', {
          timeOut: 3000,
          progressBar: true,
          enableHtml: true
         })
        this.errorMessage = error.error.title;})
    }
  }

}
