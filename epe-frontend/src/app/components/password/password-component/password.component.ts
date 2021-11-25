import { Component, OnInit } from '@angular/core';
import {PasswordTemplate} from "../password-model/password.interface";
import {PasswordService} from "../password-service/password.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {

  passwordDto? : PasswordTemplate;

  password? : FormGroup;

  // @ts-ignore
  constructor(private passwordService?: PasswordService, private formBuilder : FormBuilder) {

    this.passwordDto = {
    newPassword: '',
    oldPassword: '',
    newPasswordConfirmation: ''
    }
  }

  ngOnInit(): void {
    this.password = this.formBuilder.group({
      oldPassword: [],
      newPassword: [],
      newPasswordConfirmation: []
    })
  }

  changePassword() {
    if (this.passwordDto?.newPassword === this.passwordDto?.newPasswordConfirmation) {
      // @ts-ignore
      this.passwordService?.changePassword(this.password?.value).subscribe();
    }
  }
}
