import { Component, OnInit } from '@angular/core';
import {AssessmentInformation} from "../line-feed-models/AssessmentInformation";
import {LineFeedService} from "../line-feed-service/LineFeedService";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {User} from "../../usersview/userview-models/User";

@Component({
  selector: 'app-line-feed',
  templateUrl: './line-feed.component.html',
  styleUrls: ['./line-feed.component.css']
})
export class LineFeedComponent implements OnInit {
  jwtUser?: JwtUser;
  assessmentsInformation?: AssessmentInformation[]


  constructor(private linefeedService: LineFeedService, private jwtService: JwtService) {
    this.jwtUser = jwtService.getJwtUser();
  }

  ngOnInit(): void {

    this.linefeedService.getAssessmentInformation().subscribe(data =>{
      this.assessmentsInformation = data as AssessmentInformation[]
      console.log(data)
    })

  }



}
