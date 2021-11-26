import {Component, Inject, OnInit} from '@angular/core';
import {AssessmentView} from "../assessments-models/assessment-view.interface";
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {AbstractControl, FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {AssessmentsService} from "../assessments-services/assessments.service";
import {FeedbackView} from "../assessments-models/feedback-view.interface";
import {EvaluationFieldView} from "../assessments-models/evaluation-field-view.interface";
import {EvaluationGroupView} from "../assessments-models/evaluation-group-view.interface";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";

@Component({
  selector: 'app-assessments-evaluation',
  templateUrl: './assessments-evaluation.component.html',
  styleUrls: ['./assessments-evaluation.component.css']
})
export class AssessmentsEvaluationComponent implements OnInit {

  assessment!: AssessmentView;
  assessmentForm!: FormGroup;
  scores!: number[];
  jwtUser!: JwtUser;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any,
              public dialog: MatDialog,
              private formBuilder: FormBuilder,
              private notificationService: ToastrService,
              private assessmentService: AssessmentsService,
              private jwtService: JwtService){

  }

  ngOnInit(): void {
    this.assessment = this.data as AssessmentView;
    this.scores = [1, 2, 3, 4, 5];

    this.jwtUser = this.jwtService.getJwtUser();

    this.assessmentForm = this.formBuilder.group({
      title: [this.assessment.title],
      jobTitle: [this.assessment.jobTitle],
      evaluatorFullName: [this.jwtUser.firstname + ' ' + this.jwtUser.lastname],
      description: [this.assessment.description],
      status: [this.assessment.status],
      userId: [this.assessment.userId],
      startedById: this.jwtUser.id,
      evaluationGroups: this.formBuilder.array
        (this.assessment.evaluationGroups.map(group => this.createGroup(group))),
      personalGoals: this.formBuilder.array
        (
          this.assessment.personalGoals.length != 0
            ? this.assessment.personalGoals.map(goal => this.createGoal(goal))
            : [this.addGoal()]
        ),
      departmentGoals: this.formBuilder.array
        (
          this.assessment.departmentGoals.length != 0
            ? this.assessment.departmentGoals.map(goal => this.createGoal(goal))
            : [this.addGoal()]
        ),
      feedbacks: this.formBuilder.array
        (
          [this.addFeedback()]
        )
    });
  }

  createGroup(group: EvaluationGroupView): FormGroup {

    return this.formBuilder.group({
      title: [group.title],
      evaluationFields: this.formBuilder.array(
        // @ts-ignore
        group.evaluationFields.map(field => this.createField(field))
      )
    });
  }

  createField(field: EvaluationFieldView): FormGroup {
    return this.formBuilder.group({
      title: [field.title],
      comment: [field.comment],
      firstScore: [field.firstScore],
      secondScore: [field.secondScore],
    });
  }

  createGoal(goal: any): FormGroup {
    return this.formBuilder.group({
      goalSPart: [goal.goalSPart],
      goalMPart: [goal.goalMPart],
      goalAPart: [goal.goalAPart],
      goalRPart: [goal.goalRPart],
      goalTPart: [goal.goalTPart],
    });
  }

  createFeedback(feedback: FeedbackView): FormGroup {
    return this.formBuilder.group({
      context: [feedback.context],
      authorId: [feedback.authorId],
      authorFullName: [feedback.authorFullName]
    })
  }

  addGoal(): FormGroup {
    return this.formBuilder.group({
      goalSPart: [],
      goalMPart: [],
      goalAPart: [],
      goalRPart: [],
      goalTPart: [],
    })
  }

  addFeedback(): FormGroup {
    return this.formBuilder.group({
      context: [],
      authorFullName: [this.jwtUser.firstname + ' ' + this.jwtUser.lastname]
    })
  }

  addNewPersonalGoal() {
    this.personalGoalArray.push(this.addGoal());
  }

  removePersonalGoal(index: number) {
    this.personalGoalArray.removeAt(index);
  }

  addNewDepartmentGoal() {
    this.departmentGoalArray.push(this.addGoal());
  }

  removeDepartmentGoal(index: number) {
    this.departmentGoalArray.removeAt(index);
  }

  get personalGoalArray() {
    return <FormArray>this.assessmentForm.get('personalGoals');
  }

  get departmentGoalArray() {
    return <FormArray>this.assessmentForm.get('departmentGoals');
  }

  get feedbackArray() {
    return <FormArray>this.assessmentForm.get('feedbacks');
  }

  get evaluationGroupArray() {
    return <FormArray>this.assessmentForm.get('evaluationGroups');
  }

  evaluationFieldArray(group: AbstractControl) {
    return <FormArray>group.get('evaluationFields');
  }

  onSubmit() {

    if (this.assessmentForm.valid) {

      console.log(this.assessmentForm.value);
      this.assessmentService.evaluateAssessment(this.assessment.id,
        this.assessmentForm.value);
    } else {
      this.notificationService.error('Form should not have empty fields',
        '', {
          timeOut: 3000,
          progressBar: true
        });
    }
  }
}
