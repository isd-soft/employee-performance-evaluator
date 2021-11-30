import {Component, Inject, OnInit} from '@angular/core';
import {CancelAssessmentServices} from "../cancel-assessment-services/cancel-assessment-services";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-cancel-assessment',
  templateUrl: './cancel-assessment.component.html',
  styleUrls: ['./cancel-assessment.component.css']
})
export class CancelAssessmentComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<CancelAssessmentComponent>,
              @Inject(MAT_DIALOG_DATA) public data: string,
              public cancelAssessmentService: CancelAssessmentServices
  ) { }

  ngOnInit(): void {
  }

  setAssessmentToCancel(assessmentId: string) {
    this.cancelAssessmentService.cancelAssessment(assessmentId);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
