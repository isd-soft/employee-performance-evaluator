import {Component, Inject, Input, OnInit} from '@angular/core';
import {AssessmentTemplateView} from "../assessments-templates-models/assessment-template-view.interface";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {JobItem} from "../../home/home-models/job-item.interface";
import {HomeService} from "../../home/home-service/home.service";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {ArrayType} from "@angular/compiler";

@Component({
  selector: 'app-assessment-template-edit',
  templateUrl: './assessment-template-edit.component.html',
  styleUrls: ['./assessment-template-edit.component.css']
})
export class AssessmentTemplateEditComponent implements OnInit {

  assessmentTemplate!: AssessmentTemplateView;
  jobItems!: JobItem[];
  selectedJobTitle!: string;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any,
              private jobService: HomeService) {

  }

  ngOnInit(): void {

    this.assessmentTemplate = this.data as AssessmentTemplateView;
    this.jobService.getJobList().subscribe(data => {
      this.jobItems = data as JobItem[];
    });
    this.selectedJobTitle = this.assessmentTemplate.jobTitle;

  }

}
