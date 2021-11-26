import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {AssessmentView} from "../assessments-models/assessment-view.interface";
import {AssessmentsEvaluationComponent} from "../assessments-evaluation/assessments-evaluation.component";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";

@Component({
  selector: 'app-assessments-view',
  templateUrl: './assessments-view.component.html',
  styleUrls: ['./assessments-view.component.css']
})
export class AssessmentsViewComponent implements OnInit {

  assessment!: AssessmentView;
  jwtUser!: JwtUser;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any,
              public dialog: MatDialog,
              private jwtService: JwtService) {
    this.assessment = data as AssessmentView;
    this.jwtUser = jwtService.getJwtUser();
  }

  ngOnInit(): void {
  }

  get userId() {
    return this.jwtUser.id;
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
