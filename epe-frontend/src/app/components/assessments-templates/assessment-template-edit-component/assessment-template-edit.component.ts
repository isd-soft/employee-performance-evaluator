import {Component, Inject, Input, OnInit} from '@angular/core';
import {AssessmentTemplateView} from "../assessments-templates-models/assessment-template-view.interface";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {JobItem} from "../../home/home-models/job-item.interface";
import {HomeService} from "../../home/home-service/home.service";
import {AbstractControl, FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {ArrayType} from "@angular/compiler";
import {AssessmentsTemplatesService} from "../assessments-templates-services/assessments-templates.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-assessment-template-edit',
  templateUrl: './assessment-template-edit.component.html',
  styleUrls: ['./assessment-template-edit.component.css']
})
export class AssessmentTemplateEditComponent implements OnInit {

  assessmentTemplate!: AssessmentTemplateView;
  assessmentTemplateForm!: FormGroup;
  jobItems!: JobItem[];
  selectedJobTitle!: string;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any,
              private jobService: HomeService,
              private formBuilder: FormBuilder,
              private assessmentsTemplatesService: AssessmentsTemplatesService,
              private notificationService: ToastrService)
  {}

  ngOnInit(): void {

    this.assessmentTemplate = this.data as AssessmentTemplateView;

    this.jobService.getJobList().subscribe(data => {
      this.jobItems = data as JobItem[];
    });
    this.selectedJobTitle = this.assessmentTemplate.jobTitle;

    this.assessmentTemplateForm = this.formBuilder.group({
      title: [this.assessmentTemplate.title],
      jobTitle: [this.assessmentTemplate.jobTitle],
      description: [this.assessmentTemplate.description],
      evaluationGroups: this.formBuilder.array
        (this.assessmentTemplate.evaluationGroups.map(group => this.createGroup(group)))
    });
  }

  createGroup(group: any): FormGroup {

    return this.formBuilder.group({
      title: [group.title],
      evaluationFields: this.formBuilder.array(
        // @ts-ignore
        group.evaluationFields.map(field => this.createField(field))
      )
    });
  }

  createField(field: any): FormGroup {
    return this.formBuilder.group({
      title: [field.title],
      comment: [field.comment]
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

  removeEvaluationField(group: AbstractControl, index: number) {
    this.evaluationFieldArray(group).removeAt(index);
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
      this.assessmentsTemplatesService.updateAssessmentTemplate(this.assessmentTemplate.id,
        this.assessmentTemplateForm.value);
    } else {
      this.notificationService.error('Form should not have empty fields',
        '', {
          timeOut: 3000,
          progressBar: true
        });
    }
  }

}
