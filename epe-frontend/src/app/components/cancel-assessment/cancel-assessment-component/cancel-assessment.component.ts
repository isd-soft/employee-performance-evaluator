import {Component, Inject, OnInit} from '@angular/core';
import {CancelAssessmentServices} from "../cancel-assessment-services/cancel-assessment-services";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {AssessmentView} from "../../assessments/assessments-models/assessment-view.interface";
import {Router} from "@angular/router";

@Component({
  selector: 'app-cancel-assessment',
  templateUrl: './cancel-assessment.component.html',
  styleUrls: ['./cancel-assessment.component.css']
})
export class CancelAssessmentComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<CancelAssessmentComponent>,
              @Inject(MAT_DIALOG_DATA) public assessmentView: AssessmentView,
              public cancelAssessmentService: CancelAssessmentServices,
  ) { }

  ngOnInit(): void {
  }

  setAssessmentToCancel(assessementView: AssessmentView) {
    this.cancelAssessmentService.cancelAssessment(assessementView);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
