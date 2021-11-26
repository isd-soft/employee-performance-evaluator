import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {AssessmentView} from "../assessments-models/assessment-view.interface";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";

@Injectable({
  providedIn: 'root'
})
export class AssessmentsService {

  assessmentsUrl: string = 'api-server/api/';


  constructor(private http: HttpClient,
              private notificationService: ToastrService,
              private router: Router,
              private route: ActivatedRoute,
              private dialogRef: MatDialog,
              private jwtService: JwtService) { }

  getAllAssessmentsByUserId(userId: string) {
    return this.http.get(this.assessmentsUrl + 'users/' + userId + '/assessments')
      .pipe(catchError(this.errorHandler));
  }

  getAllAssessmentsByUserIdAndStatus(userId: string, status: string) {
    return this.http.get(this.assessmentsUrl + 'users/' + userId + '/assessments?status=' + status)
      .pipe(catchError(this.errorHandler));
  }

  getAllAssignedAssessments(userId: string) {
    return this.http.get(this.assessmentsUrl + 'users/' + userId + '/assigned-assessments')
      .pipe(catchError(this.errorHandler));
  }

  getAllAssignedAssessmentsByStatus(userId: string, status: string) {
    return this.http.get(this.assessmentsUrl + 'users/' + userId + '/assigned-assessments?status=' + status)
      .pipe(catchError(this.errorHandler));
  }

  evaluateAssessment(assessmentId: string, assessment: AssessmentView) {
    return this.http.put(this.assessmentsUrl + 'users/' + this.jwtService.getJwtUser().id + '/assessments/'
      + assessmentId, assessment,
      {observe: 'response'}).subscribe(response => {
      this.reload();
      this.closeDialogs();
      this.notificationService.success('Assessment was evaluated successfully',
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
    this.router.navigate(['/assessments']);
  }

  closeDialogs() {
    this.dialogRef.closeAll();
  }

  getAllAssessments(){
    return this.http.get("api-server/api/assessments");
  }

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }
}
