import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/jwt/jwt-service.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent {

  constructor(private jwtService: JwtService,
    private router: Router) {
      this.jwtService.deleteJWT();
      this.router.navigate(['/login']);
    }
}
