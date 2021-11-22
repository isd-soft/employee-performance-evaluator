import { Component, OnInit } from '@angular/core';
import {HomeService} from "../../home/home-service/home.service";
import {JobItem} from "../../home/home-models/job-item.interface";
import {AbstractControl, FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {group} from "@angular/animations";

@Component({
  selector: 'app-assessment-template-create',
  templateUrl: './assessment-template-create.component.html',
  styleUrls: ['./assessment-template-create.component.css']
})
export class AssessmentTemplateCreateComponent implements OnInit {

  jobItems!: JobItem[];
  assessmentTemplateForm!: FormGroup;

  constructor(private homeService: HomeService,
              private formBuilder: FormBuilder) { }

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
      title: []
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
    console.log(this.assessmentTemplateForm.value);
  }

}
