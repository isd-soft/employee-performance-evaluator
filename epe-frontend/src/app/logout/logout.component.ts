import { JwtService } from './../decoder/decoder-service/jwt.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private jwtService: JwtService,
              private router: Router) { }

  ngOnInit(): void {
    console.log('logout')
    this.jwtService.removeJWT()
    this.router.navigate([''])
  }
}
