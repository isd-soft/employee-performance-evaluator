import { Component } from '@angular/core';
import { JwtService } from 'src/app/jwt/jwt-service.service';
import { Jwt } from 'src/app/models/jwt.model';

@Component({
  selector: 'app-admin-sidebar',
  templateUrl: './admin-sidebar.component.html',
  styleUrls: ['./admin-sidebar.component.css']
})
export class AdminSidebarComponent {

  jwtUser?: Jwt;
  myProfileOpenState = false;

  constructor(private jwtService: JwtService) {
    this.jwtUser = this.jwtService.getJwtUser();
  }

}
