import { Component, OnInit } from '@angular/core';
import { JwtUser } from '../decoder/decoder-model/jwt-user.interface';
import { JwtService } from '../decoder/decoder-service/jwt.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  loggedUser?: JwtUser

  constructor(private jwtService: JwtService) { }

  ngOnInit(): void {
    this.loggedUser = this.jwtService.getJwtUser()
    console.log(this.loggedUser)

    let canvas = document.getElementById('myCanvas') as HTMLCanvasElement;
    let context = canvas.getContext('2d');
    let imageObj = new Image();
    imageObj.onload = function () {
      if(context) {
        context.drawImage(imageObj, 69, 50);
        context.font = "20px Calibri";
        context.fillText("My TEXT!", 100, 200);
      }
    };

    imageObj.src = 'http://www.html5canvastutorials.com/demos/assets/darth-vader.jpg';
  }
}