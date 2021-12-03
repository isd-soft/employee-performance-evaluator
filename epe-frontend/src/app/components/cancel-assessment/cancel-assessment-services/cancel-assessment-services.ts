import {HttpClient, HttpResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {Injectable} from "@angular/core";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {AssessmentView} from "../../assessments/assessments-models/assessment-view.interface";
import {Observable, Subscription} from "rxjs";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CancelAssessmentServices{
  baseUrl = environment.baseUrl;
  assessment!: AssessmentView
  constructor(private http: HttpClient, private jwtService: JwtService, private notificationService: ToastrService) {

  }

  cancelAssessment(assessmentView: AssessmentView, cancelReason: string){
    console.log(assessmentView)
    assessmentView.status = "CANCELED";
    assessmentView.cancellationReason = cancelReason
    assessmentView.startedById = this.jwtService.getJwtUser().id;
    // console.log(this.http.get<AssessmentView>("api-server/api/assessments/" + assessmentView));
    // console.log(this.assessment)
    this.http.put(this.baseUrl + "api/assessments/" + assessmentView.id, assessmentView ).subscribe(response => {
      this.notificationService.success('Assessment was evaluated canceled',
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
    // console.log(assessmentView)
  }
}
