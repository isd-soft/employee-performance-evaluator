import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {AssessmentInformation} from "../line-feed-models/AssessmentInformation";
import {FormBuilder, FormGroup} from "@angular/forms";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {FeedbackService} from "../feedback-service/feedback.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-leave-feedback',
  templateUrl: './leave-feedback.component.html',
  styleUrls: ['./leave-feedback.component.css']
})
export class LeaveFeedbackComponent implements OnInit {

  assessment: AssessmentInformation;
  feedbackForm!: FormGroup;
  jwtUser: JwtUser;

  constructor(@Inject(MAT_DIALOG_DATA) private data: AssessmentInformation,
              private formBuilder: FormBuilder,
              private jwtService: JwtService,
              private feedbackService: FeedbackService,
              private notificationService: ToastrService) {
    this.assessment = data;
    this.jwtUser = this.jwtService.getJwtUser();
    this.feedbackForm = this.formBuilder.group({
      context: [],
      authorId: [this.jwtUser.id],
      authorFullName: [this.jwtUser.firstname + ' ' + this.jwtUser.lastname]
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {

    if (this.feedbackForm.valid) {
      this.feedbackService.addFeedback(this.feedbackForm.value,
        this.assessment);
    } else {
      this.notificationService.error('Form should not have empty fields',
        '', {
          timeOut: 3000,
          progressBar: true
        });
    }
  }

}
