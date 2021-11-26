import { Component } from '@angular/core';
import {JwtService} from "./decoder/decoder-service/jwt.service";
import {JwtUser} from "./decoder/decoder-model/jwt-user.interface";
import { interval } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'epe';
  jwtUser?: JwtUser;
  isUser?: boolean = false;
  isAdmin?: boolean = false;
  isSysAdmin?: boolean = false;

  constructor(private jwtService: JwtService) {

    interval(100).subscribe(x => {
      this.jwtUser = this.jwtService.getJwtUser();

      if(this.jwtUser) {
        if(this.jwtUser.role == 'ROLE_SYSADMIN') {
          this.isSysAdmin = true;
          this.isAdmin = true;
          this.isUser = true;
        }

        if(this.jwtUser.role == 'ROLE_ADMIN') {
          this.isSysAdmin = false;
          this.isAdmin = true;
          this.isUser = true;
        }
        if(this.jwtUser.role == 'ROLE_USER') {
          this.isSysAdmin = false;
          this.isAdmin = false;
          this.isUser = true;
        }
      }
    });
  }
}
