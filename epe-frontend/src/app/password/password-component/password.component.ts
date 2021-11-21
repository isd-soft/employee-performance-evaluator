import { Component, OnInit } from '@angular/core';
import {PasswordTemplate} from "../password-model/password.interface";

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {

  passwordDto? : PasswordTemplate;

  errorMessage? : string;

  constructor() { }

  ngOnInit(): void {
  }

  changePassword() {

  }
}
