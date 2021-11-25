import {Component, Inject, OnInit} from '@angular/core';
import {AssessmentService} from "../services/Assessment-service";
import {AssessmentTemplate} from "../assessment-model/Assessment-template";
import {UserComponent} from "../../user/user-component/user.component";
import {UserTemplate} from "../assessment-model/User-template";
import {StartAssessment} from "../assessment-model/StartAssessment";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";

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
  //TODO make a interface for assessment templates
  constructor( @Inject(MAT_DIALOG_DATA) public userId: string,
               public dialogRef: MatDialogRef<AssessmentComponent>,
               private assessmentService: AssessmentService,
               private formBuilder: FormBuilder,
               private router: Router,
               public dialog: MatDialog,) {

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
  }

  startAssess() {
    this.assessmentService.startAssessment(this.userId, this.startAssessment?.value.assessmentTemplateId)
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/usersview'])
    this.dialogRef.close();
  }

}
