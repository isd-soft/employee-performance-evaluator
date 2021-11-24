import { Component, OnInit } from '@angular/core';
import {HomeService} from "../../home/home-service/home.service";
import {JobItem} from "../../home/home-models/job-item.interface";
import {AbstractControl, FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {AssessmentsTemplatesService} from "../assessments-templates-services/assessments-templates.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-assessment-template-create',
  templateUrl: './assessment-template-create.component.html',
  styleUrls: ['./assessment-template-create.component.css']
})
export class AssessmentTemplateCreateComponent implements OnInit {

  jobItems!: JobItem[];
  assessmentTemplateForm!: FormGroup;

  constructor(private homeService: HomeService,
              private assessmentsTemplatesService: AssessmentsTemplatesService,
              private formBuilder: FormBuilder,
              private notificationService: ToastrService) { }

  ngOnInit(): void {

    this.homeService.getJobList().subscribe(data => {
      this.jobItems = data as JobItem[];
    });

    this.assessmentTemplateForm = this.formBuilder.group({
      title: [],
      jobTitle: [],
      description: [],
      evaluationGroups: this.formBuilder.array([this.addEvaluationGroup()])
    });
  }

  addEvaluationGroup() {
    return this.formBuilder.group({
      title: [],
      evaluationFields: this.formBuilder.array([this.addEvaluationField()])
    });
  }

  addEvaluationField() {
    return this.formBuilder.group({
      title: [],
      comment: []
    });
  }

  addNewEvaluationGroup() {
    this.evaluationGroupArray.push(this.addEvaluationGroup());
  }

  removeEvaluationGroup(index: number) {
    this.evaluationGroupArray.removeAt(index);
  }

  addNewEvaluationField(group: AbstractControl) {
    this.evaluationFieldArray(group).push(this.addEvaluationField());
  }

  removeEvaluationField(group: AbstractControl, index: number, groupIndex: number) {
    this.evaluationFieldArray(group).removeAt(index);
    if (this.evaluationFieldArray(group).length == 0) {
      this.removeEvaluationGroup(groupIndex);
    }
  }

  get evaluationGroupArray() {
    return <FormArray>this.assessmentTemplateForm.get('evaluationGroups');
  }

  evaluationFieldArray(group: AbstractControl) {
    return <FormArray>group.get('evaluationFields');
  }

  onSubmit() {

    let hasEmptyGroups: boolean = false;

    this.evaluationGroupArray.controls.forEach(group => {
      if (this.evaluationFieldArray(group).length == 0) {
        hasEmptyGroups = true;
      }
    })

    if (this.assessmentTemplateForm.valid && this.evaluationGroupArray.length != 0 && !hasEmptyGroups) {
      this.assessmentsTemplatesService.saveAssessmentTemplate(this.assessmentTemplateForm.value);
    } else {
      this.notificationService.error('Form should not have empty fields',
        '', {
          timeOut: 3000,
          progressBar: true
        });
    }
  }


}
