import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {AssessmentView} from "../assessments-models/assessment-view.interface";
import {AssessmentsEvaluationComponent} from "../assessments-evaluation/assessments-evaluation.component";

@Component({
  selector: 'app-assessments-view',
  templateUrl: './assessments-view.component.html',
  styleUrls: ['./assessments-view.component.css']
})
export class AssessmentsViewComponent implements OnInit {

  assessment!: AssessmentView;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any,
              public dialog: MatDialog) {
    this.assessment = data as AssessmentView;
    console.log(this.assessment.departmentGoals);
  }

  ngOnInit(): void {
  }

  openEvaluationDialog() {
    this.dialog.open(AssessmentsEvaluationComponent, {
      height: '90%',
      width: '90%',
      data : this.assessment,
      autoFocus: false
    });
  }

}
