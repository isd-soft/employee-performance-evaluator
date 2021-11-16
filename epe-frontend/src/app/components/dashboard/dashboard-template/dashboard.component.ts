import { Component, OnInit } from '@angular/core';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  loggedUser?: JwtUser

  constructor(private jwtService: JwtService) {
    this.loggedUser = this.jwtService.getJwtUser()
   }
}
