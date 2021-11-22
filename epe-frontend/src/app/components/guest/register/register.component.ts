import { RegisterRequest } from '../../../models/register-request.model';
import { AuthenticationService } from '../../../services/auth/authentication.service';
import { Component } from '@angular/core';
import { JwtService } from 'src/app/jwt/jwt-service.service';
import { JobItem } from 'src/app/models/job-item.model';
import { Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  firstnameFormControl = new FormControl('', [Validators.required]);
  lastnameFormControl = new FormControl('', [Validators.required]);
  birthdateFormControl = new FormControl('', [Validators.required]);
  phoneFormControl = new FormControl('', [Validators.required]);
  jobFormControl = new FormControl();
  jobList?: JobItem[];
  employmentdateFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);

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
    private authService: AuthenticationService,
    private router: Router) {

      this.authService.getJobList().subscribe(data => {
      this.jobList = data as JobItem[];
      });
    }

    register() {

      let datePipe = new DatePipe('en-US');
      this.registerUser.birthDate = datePipe.transform(this.registerUser.birthDate, 'dd-MM-yyyy') as string,
      this.registerUser.employmentDate = datePipe.transform(this.registerUser.employmentDate, 'dd-MM-yyyy') as string,


      this.authService.register(this.registerUser).subscribe(data => {
      }, error => {
         if(error.status == 200) {
           this.router.navigate(['/home']);
         }
         this.errorMessage = error.error.title;});
    }
}
