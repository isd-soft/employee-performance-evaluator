import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FeedbackView} from "../../assessments/assessments-models/feedback-view.interface";
import {AssessmentInformation} from "../line-feed-models/AssessmentInformation";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  usersUrl: string = 'api-server/api/users/'

  constructor(private httpClient: HttpClient,
              private router: Router,
              private dialogRef: MatDialog,
              private notificationService: ToastrService) { }

  addFeedback(feedback: FeedbackView, assessment: AssessmentInformation): void {
    this.httpClient.post(this.usersUrl + assessment.evaluatedUserId + '/assessments/' + assessment.assessmentId + '/feedbacks', feedback,
      {observe: 'response'}).subscribe(response => {
      this.reload();
      this.closeDialogs();
      this.notificationService.success('Feedback was posted successfully',
        '', {
          timeOut: 3000,
          progressBar: true
        });
    }, error => {
      let message: string;
      switch (error.status) {
        case 400:
          message = "Bad request. " + error.error.title;
          break;
        case 401:
          message = "Unauthorized. " + error.error.title;
          break;
        default:
          message = "Error! " + error.message;
      }
      this.notificationService.error(message,
        '', {
          timeOut: 3000,
          progressBar: true
        });
    });
  }

  reload() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/dashboard']);
  }

  closeDialogs() {
    this.dialogRef.closeAll();
  }
}
