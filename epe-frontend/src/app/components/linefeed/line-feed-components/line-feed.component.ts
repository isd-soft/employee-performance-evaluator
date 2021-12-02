import { Component, OnInit } from '@angular/core';
import {AssessmentInformation} from "../line-feed-models/AssessmentInformation";
import {LineFeedService} from "../line-feed-service/LineFeedService";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {MatDialog} from "@angular/material/dialog";
import {LeaveFeedbackComponent} from "../leave-feedback/leave-feedback.component";

@Component({
  selector: 'app-line-feed',
  templateUrl: './line-feed.component.html',
  styleUrls: ['./line-feed.component.css']
})
export class LineFeedComponent implements OnInit {

  assessmentsInformation!: AssessmentInformation[]
  userId: string;

  constructor(private linefeedService: LineFeedService,
              private jwtService: JwtService,
              private dialog: MatDialog) {
    this.userId = this.jwtService.getJwtUser().id;
  }

  ngOnInit(): void {

    this.linefeedService.getAssessmentInformation().subscribe(data =>{
      this.assessmentsInformation = data as AssessmentInformation[];
    })

  }

  openDialog(id: number) {
    this.dialog.open(LeaveFeedbackComponent, {
      height: '85%',
      width: '85%',
      data : this.assessmentsInformation[id],
      autoFocus: false
    });
  }



}
