import { JobItem } from './../home-models/job-item.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';
import { LoginResponse } from './../home-models/login-response.interface';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from '../home-models/login-request.interface';
import { HomeService } from '../home-service/home.service';
import { RegisterRequest } from '../home-models/register-request.interface';
import {FormControl, Validators} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  hasAccount = true;

  loginUser: LoginRequest = {
    email: '',
    password: ''
  };

  loginEmailFormControl = new FormControl('', [Validators.required, Validators.email]);
  loginPasswordFormControl = new FormControl('', [Validators.required]);


  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  firstnameFormControl = new FormControl('', [Validators.required]);
  lastnameFormControl = new FormControl('', [Validators.required]);
  birthdateFormControl = new FormControl('', [Validators.required]);
  phoneFormControl = new FormControl('', [Validators.required]);
  jobFormControl = new FormControl();
  jobList?: JobItem[];
  employmentdateFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);
  confirmPasswordFormControl = new FormControl('', [Validators.required]);

  registerUser: RegisterRequest = {
    email: '',
    firstname: '',
    lastname: '',
    birthDate: '',
    employmentDate: '',
    phoneNumber: '',
    job: '',
    bio: 'new user',
    password: ''
  };

  confirmPassword?: string;

  errorMessage?: string;

  constructor(private jwtService: JwtService,
              private homeService: HomeService,
              private toastr: ToastrService,
              private router: Router) {

    this.homeService.getJobList().subscribe(data => {
      this.jobList = data as JobItem[];
    })
   }

   requestRegisterPage() {
    this.errorMessage = undefined;
    this.hasAccount = false;
   }

   requestLoginPage() {
    this.errorMessage = undefined;
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
         this.toastr.error('Something went wrong .. </br> Please check your credentials','', {
          timeOut: 3000,
          progressBar: true,
          enableHtml: true
         })
        this.errorMessage = error.error.title;
      })
    }
  }


  register() {

    if(this.registerUser.password == this.confirmPassword) {
      let datePipe = new DatePipe('en-US');
      this.registerUser.birthDate = datePipe.transform(this.registerUser.birthDate, 'dd-MM-yyyy') as string,
      this.registerUser.employmentDate = datePipe.transform(this.registerUser.employmentDate, 'dd-MM-yyyy') as string,


      this.homeService.register(this.registerUser).subscribe(data => {
      }, error => {
         if(error.status == 200) {
           this.toastr.success('Your account had been created ! </br> You will be redirected to login page', '', {
             timeOut: 3000,
             progressBar: true,
             enableHtml: true
           });
           setTimeout(()=> {
            this.requestLoginPage();
           }, 4000);
         } else {
          this.errorMessage = error.error.title;
           this.toastr.error('Something went wrong .. </br> Please try again in a few seconds','', {
            timeOut: 3000,
            progressBar: true,
            enableHtml: true
           });
         }});
    } else {
      this.toastr.error('Passwords do not match !','', {
        timeOut: 3000,
        progressBar: true,
        enableHtml: true
       });
    }
  }

}
