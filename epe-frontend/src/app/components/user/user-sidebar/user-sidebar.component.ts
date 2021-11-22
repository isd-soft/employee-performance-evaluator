import { JwtService } from '../../../jwt/jwt-service.service';
import { Jwt } from '../../../models/jwt.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-sidebar',
  templateUrl: './user-sidebar.component.html',
  styleUrls: ['./user-sidebar.component.css']
})
export class UserSidebarComponent {

  jwtUser?: Jwt;
  myProfileOpenState = false;

  constructor(private jwtService: JwtService) {
    this.jwtUser = this.jwtService.getJwtUser();
  }

}
