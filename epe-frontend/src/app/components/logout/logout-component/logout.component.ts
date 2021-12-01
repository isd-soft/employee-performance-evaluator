import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent {

  constructor(private jwtService: JwtService,
              private router: Router) {
    this.jwtService.removeJWT();
    this.removeAllFromLocalStorage();
    this.router.navigate(['/home']);
  }

  removeAllFromLocalStorage() {
    localStorage.removeItem('MY_PROFILE_RELOADED');
  }
}
