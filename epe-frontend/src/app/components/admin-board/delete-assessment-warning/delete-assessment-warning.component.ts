import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AssessmentView} from "../../assessments/assessments-models/assessment-view.interface";
import {AdminBoardService} from "../admin-board-service/AdminBoardService";
import {Router} from "@angular/router";

@Component({
  selector: 'app-delete-assessment-warning',
  templateUrl: './delete-assessment-warning.component.html',
  styleUrls: ['./delete-assessment-warning.component.css']
})
export class DeleteAssessmentWarningComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public assessmentId: string,
              public dialogRef: MatDialogRef<DeleteAssessmentWarningComponent>,
              private boardService: AdminBoardService,
              private router: Router) { }

  ngOnInit(): void {
  }

  cancel() {
    this.dialogRef.close();
  }

  deleteAssessment() {
    console.log(this.assessmentId);
    this.boardService.deleteAssessment(this.assessmentId);
    this.dialogRef.close()
    this.reload()
    // this.router.
  }

  reload() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/admin-board']);
  }
}
