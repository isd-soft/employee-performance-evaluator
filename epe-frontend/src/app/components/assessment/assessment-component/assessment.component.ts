import { Component, OnInit } from '@angular/core';
import {AssessmentService} from "../services/Assessment-service";
import {AssessmentTemplate} from "../assessment-model/Assessment-template";
import {UserComponent} from "../../user/user-component/user.component";
import {UserTemplate} from "../assessment-model/User-template";
import {StartAssessment} from "../assessment-model/StartAssessment";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-assessment-template',
  templateUrl: './assessment.component.html',
  styleUrls: ['./assessment.component.css']
})

export class AssessmentComponent implements OnInit {
  // @ts-ignore
  assessmentTemplates : AssessmentTemplate[];
  // @ts-ignore
  assignableUser: UserTemplate[];
  // @ts-ignore
  startAssessment?: FormGroup;
  errorMesage: string = "afa";
  //TODO make a interface for assessment templates
  constructor(private assessmentService: AssessmentService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {

    this.startAssessment = this.formBuilder.group(
      {
        userId: [],
        assessmentTemplateId: []
      }
    )

    this.assessmentService.getAssessmentTemplates().subscribe(assessmentTemplate => {
      this.assessmentTemplates = assessmentTemplate as AssessmentTemplate[]
      })

    this.assessmentService.getAssignedUsers().subscribe(user => {
      this.assignableUser = user as UserTemplate[]
    })

  }


  startAssess() {
    this.assessmentService.startAssessment(this.startAssessment?.value.userId, this.startAssessment?.value.assessmentTemplateId)
    // this.errorMesage = this.assessmentService.startAssessment(this.startAssessment?.value.userId, this.startAssessment?.value.assessmentTemplateId);
  }
}
