import {AfterViewInit, Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {AssessmentTemplateView} from "../assessments-templates-models/assessment-template-view.interface";
import {AssessmentTemplateEditComponent} from "../assessment-template-edit-component/assessment-template-edit.component";

@Component({
  selector: 'app-assessment-template-view',
  templateUrl: './assessment-template-view.component.html',
  styleUrls: ['./assessment-template-view.component.css']
})
export class AssessmentTemplateViewComponent implements OnInit {

  assessmentTemplate!: AssessmentTemplateView;

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    public dialog: MatDialog)
  {
    this.assessmentTemplate = data as AssessmentTemplateView;
  }

  ngOnInit(): void {
  }

  openEditDialog() {
    this.dialog.open(AssessmentTemplateEditComponent, {
      height: '90%',
      width: '90%',
      data : this.assessmentTemplate
    });
  }

}
