import { Component, OnInit } from '@angular/core';
import {PasswordTemplate} from "../password-model/password.interface";
import {PasswordService} from "../password-service/password.service";

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {

  passwordDto? : PasswordTemplate;

  errorMessage? : string;

  constructor(private passwordService?: PasswordService) {

    this.passwordDto = {
    newPassword: '',
    oldPassword: '',
    newPasswordConfirmation: ''
    }
  }

  ngOnInit(): void {
  }

  changePassword() {
    console.log(this.passwordDto)
    if (this.passwordDto?.newPassword === this.passwordDto?.newPasswordConfirmation) {
      // @ts-ignore
      this.passwordService?.changePassword(this.passwordDto).subscribe(data => {
      }, error => {
        this.errorMessage = error.error.title;
      });
    }
  }
}
