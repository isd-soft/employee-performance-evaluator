import {HttpClient, HttpResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {Injectable} from "@angular/core";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {AssessmentView} from "../../assessments/assessments-models/assessment-view.interface";
import {Observable, Subscription} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CancelAssessmentServices{
  assessment!: AssessmentView
  constructor(private http: HttpClient, private jwtService: JwtService) {

  }

  cancelAssessment(assessmentView: AssessmentView){
    console.log(assessmentView)
    assessmentView.status = "CANCELED";
    assessmentView.startedById = this.jwtService.getJwtUser().id;
    // console.log(this.http.get<AssessmentView>("api-server/api/assessments/" + assessmentView));
    // console.log(this.assessment)
    this.http.put("api-server/api/assessments/" + assessmentView.id, assessmentView ).subscribe()
    // console.log(assessmentView)
  }
}
