import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError } from 'rxjs';
import { Injectable } from '@angular/core';
import jwt_decode from "jwt-decode";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {catchError} from "rxjs/operators";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})

export class AssessmentService {
  // @ts-ignore
  // id: string = this.jwtUser.id | "19e23536-f565-4d86-a69e-ffb8fba0ac70";
  jwtUser?: JwtUser;


  assessmentsTemplatesUrl: string = 'api-server/api/assessments-templates';
  // assignedUserUrl: string = "api-server/api/" + this.id + "/assignedUsers"


  constructor(private http: HttpClient, private jwtService: JwtService, private notificationService: ToastrService) {
    this.jwtUser = this.jwtService.getJwtUser();
  }

  getAssessmentTemplates() {
    return this.http.get(this.assessmentsTemplatesUrl);
  }

  getAssignedUsers(){
    if (this.jwtUser?.role == "ROLE_ADMIN"){
      return this.http.get("api-server/api/users/");
    }
    return this.http.get("api-server/api/users/" + this.jwtUser?.id + "/assignedUsers");
  }

  startAssessment(userId: string, assessmentTemplateId: string) {
    let startAssessmentUrl = " api-server/api/users/" +  userId + "/assessments"

    this.http.post(startAssessmentUrl, {id: assessmentTemplateId}).subscribe(
      data => {
        this.notificationService.success("Success",'',{
          timeOut: 3000,
          progressBar: true,
        })
        console.log('Success')
      }, error => {
        this.notificationService.error(error.error.title,'',{
          timeOut: 3000,
          progressBar: true,
        })
        console.log('Error')
      },
    );
  }
}
