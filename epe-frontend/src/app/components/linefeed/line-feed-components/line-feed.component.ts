import { Component, OnInit } from '@angular/core';
import {AssessmentInformation} from "../line-feed-models/AssessmentInformation";
import {LineFeedService} from "../line-feed-service/LineFeedService";

@Component({
  selector: 'app-line-feed',
  templateUrl: './line-feed.component.html',
  styleUrls: ['./line-feed.component.css']
})
export class LineFeedComponent implements OnInit {

  assessmentsInformation?: AssessmentInformation[]

  constructor(private linefeedService: LineFeedService) { }

  ngOnInit(): void {

    this.linefeedService.getAssessmentInformation().subscribe(data =>{
      this.assessmentsInformation = data as AssessmentInformation[];
      console.log(this.assessmentsInformation);
    })

  }



}
