import {AfterViewInit, Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {AssessmentTemplateView} from "../assessments-templates-models/assessment-template-view.interface";
import {AssessmentTemplateEditComponent} from "../assessment-template-edit-component/assessment-template-edit.component";
import {DeleteConfirmationDialogComponent} from "./delete-confirmation-dialog.component";
import {AssessmentsTemplatesService} from "../assessments-templates-services/assessments-templates.service";

@Component({
  selector: 'app-assessment-template-view',
  templateUrl: './assessment-template-view.component.html',
  styleUrls: ['./assessment-template-view.component.css']
})
export class AssessmentTemplateViewComponent implements OnInit {

  assessmentTemplate!: AssessmentTemplateView;

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    public dialog: MatDialog,
    private assessmentsTemplatesService: AssessmentsTemplatesService)
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

  openConfirmDialog() {
    const dialogRef = this.dialog.open(DeleteConfirmationDialogComponent,{
      data:{
        message: 'Are you sure want to delete?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No'
        }
      }
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {

      if (confirmed) {
        this.assessmentsTemplatesService.deleteAssessmentTemplate(this.assessmentTemplate.id);
      }

    });
  }
}
