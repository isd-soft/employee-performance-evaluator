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
  firstnameFormControl = new FormControl('', [Validators.required, Validators.pattern("^[A-Z][A-Za-z '-]{2,19}$")]);
  lastnameFormControl = new FormControl('', [Validators.required, Validators.pattern("^[A-Z][A-Za-z '-]{2,19}$")]);
  birthdateFormControl = new FormControl('', [Validators.required]);
  phoneFormControl = new FormControl('', [Validators.required, Validators.pattern("^[+]*[0-9]{9,15}$")]);
  jobFormControl = new FormControl();
  jobList?: JobItem[];
  employmentdateFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required, Validators.pattern("^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,30}$")]);
  confirmPasswordFormControl = new FormControl('', [Validators.required, Validators.pattern("^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,30}$")]);

  registerUser: RegisterRequest = {
    email: '',
    firstname: '',
    lastname: '',
    birthDate: '',
    employmentDate: '',
    phoneNumber: '',
    job: '',
    bio: 'here goes your bio',
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
          this.toastr.success('Login succesful !', '', {
            timeOut: 3000,
            progressBar: true,
            enableHtml: true
          });
          setTimeout(()=> {
            this.jwtService.storeJWT(response.token);
            window.location.href = '/dashboard';
           }, 100);
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

    if(this.validateNewUser()) {
      let datePipe = new DatePipe('en-US');
      this.registerUser.birthDate = datePipe.transform(this.registerUser.birthDate, 'dd-MM-yyyy') as string,
      this.registerUser.employmentDate = datePipe.transform(this.registerUser.employmentDate, 'dd-MM-yyyy') as string,


      this.homeService.register(this.registerUser).subscribe(data => {
        this.toastr.success('Your account had been created ! </br> You will be redirected to login page', '', {
          timeOut: 3000,
          progressBar: true,
          enableHtml: true
        });
        setTimeout(()=> {
          this.loginUser = {
            email: this.registerUser.email,
            password: this.registerUser.password
          };
  
          this.login();
        }, 1000);
      }, error => {
          this.errorMessage = error.error.title;
           this.toastr.error('Something went wrong .. </br> Please try again in a few seconds','', {
            timeOut: 3000,
            progressBar: true,
            enableHtml: true
           });
         });
    } else {
      this.toastr.error('Please complete all fields and make sure the passwords match!','', {
        timeOut: 3000,
        progressBar: true,
        enableHtml: true
       });
    }
  }

  validateNewUser() {
    
    if(this.registerUser.password == this.confirmPassword)
      if(this.registerUser.email && this.emailFormControl.valid)
        if(this.registerUser.firstname && this.firstnameFormControl.valid)
          if(this.registerUser.lastname && this.lastnameFormControl.valid)
            if(this.registerUser.birthDate && this.birthdateFormControl.valid)
              if(this.registerUser.employmentDate && this.employmentdateFormControl.valid)
                if(this.registerUser.phoneNumber && this.phoneFormControl.valid)
                  if(this.registerUser.job && this.jobFormControl.valid)
                    return true;

    return false;
  }
}
