import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {throwError as observableThrowError} from "rxjs/internal/observable/throwError";
import {catchError} from "rxjs/operators";
import {AssessmentTemplateView} from "../assessments-templates-models/assessment-template-view.interface";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";

@Injectable({
  providedIn: 'root'
})
export class AssessmentsTemplatesService {

  // assessmentsTemplatesUrl: string = 'api-server/api/assessments-templates';
  assessmentsTemplatesUrl: string = 'http://localhost:8075/api/assessments-templates';

  constructor(private http: HttpClient,
              private notificationService: ToastrService,
              private router: Router,
              private route: ActivatedRoute,
              private dialogRef: MatDialog) { }

  getAllAssessmentsTemplates() {
    return this.http.get(this.assessmentsTemplatesUrl)
                    .pipe(catchError(this.errorHandler));
  }

  getAssessmentTemplate(id: string) {
    return this.http.get(this.assessmentsTemplatesUrl + '/' + id)
                    .pipe(catchError(this.errorHandler));
  }

  saveAssessmentTemplate(assessmentTemplate: AssessmentTemplateView) {

    this.http.post(this.assessmentsTemplatesUrl, assessmentTemplate,
      {observe: 'response'})
      .subscribe(response => {

        this.reload();
        this.notificationService.success('Assessment template was created successfully',
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
            message = "Unauthorized"
            break;
          default:
            message = "Unknown error"
        }
          this.notificationService.error(message,
            '', {
              timeOut: 3000,
              progressBar: true
            });
      });
  }

  updateAssessmentTemplate(id: string, assessmentTemplate: AssessmentTemplateView) {
    this.http.put(this.assessmentsTemplatesUrl + '/' + id, assessmentTemplate,
      {observe: 'response'})
      .subscribe(response => {

        this.closeDialogs();
        this.reload();
        this.notificationService.success('Assessment template was updated successfully',
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
            message = "Unauthorized"
            break;
          default:
            message = "Unknown error"
        }
        this.notificationService.error(message,
          '', {
            timeOut: 3000,
            progressBar: true
          });
      });
  }

  deleteAssessmentTemplate(id: string) {
    this.http.delete(this.assessmentsTemplatesUrl + '/' + id,
      {observe: 'response'})
      .subscribe(response => {

        this.closeDialogs();
        this.reload();
        this.notificationService.success('Assessment was deleted successfully',
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
            message = "Unauthorized";
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

  errorHandler(error: HttpErrorResponse){
    return observableThrowError(error);
  }

  reload() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/assessments-templates']);
  }

  redirectTo(uri:string){
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
      this.router.navigate([uri]));
  }

  closeDialogs() {
    this.dialogRef.closeAll();
  }


}
