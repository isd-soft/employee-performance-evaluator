import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';
import { JwtUser } from 'src/app/decoder/decoder-model/jwt-user.interface';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  loggedUser?: JwtUser

  constructor(private jwtService: JwtService) {
    this.loggedUser = this.jwtService.getJwtUser()
   }

  ngOnInit(): void {
    console.log('here ???')
  }

}
