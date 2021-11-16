import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  jwtUser?: JwtUser;
  isUser?: boolean;
  isAdmin?: boolean;

  constructor(private jwtService: JwtService) {
    
    this.jwtUser = this.jwtService.getJwtUser();

    if(this.jwtUser) {
      if(this.jwtUser.role == 'ROLE_ADMIN' || this.jwtUser.role == 'ROLE_SYSADMIN') {
        this.isAdmin = true;
        this.isUser = true;
      }

      if(this.jwtUser.role == 'ROLE_USER') {
        this.isUser = true;
      }
    }
  }
}
