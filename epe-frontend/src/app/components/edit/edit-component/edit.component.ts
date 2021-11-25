import {Component, Injectable, OnInit} from '@angular/core';
import {JobItem} from '../edit-models/job-item.interface';
import {EditFiller} from './edit.filler';
import {User} from '../edit-models/user.interface';
import {JwtService} from 'src/app/decoder/decoder-service/jwt.service';
import {EditService} from '../edit-service/edit.service';
import {Router} from '@angular/router';
import {DatePipe} from "@angular/common";
import {Observable, ReplaySubject} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
@Injectable({providedIn: 'root'})
export class EditComponent implements OnInit {

  user!: FormGroup;

  jobList?: JobItem[];

  auxUser!: User;

  roles?: string[];
  role? : string;
  requiredRole : string = "ROLE_SYSADMIN";


  constructor(private jwtService: JwtService,
              private editService: EditService,
              private formBuilder: FormBuilder) {

  }

  url = "";

  base64Output? : string;

  // @ts-ignore
  selectFile(event) {
    if (event.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (event : any) => {
        this.url = event.target.result;
      }
      this.convertFile(event.target.files[0]).subscribe(base64 => {
        this.base64Output = base64;
      });
    }
  }

  convertFile(file : File) : Observable<string> {
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    // @ts-ignore
    reader.onload = (event) => result.next(btoa(event.target.result.toString()));
    return result;
  }

  update() {
    // @ts-ignore
    this.auxUser.image = this.base64Output;
    // @ts-ignore
    this.editService.update(this.user?.value);

  }

  ngOnInit(): void {
    this.editService.getUser().subscribe(data =>
      this.auxUser = data as User);
    this.editService.getJobList().subscribe(data =>
      this.jobList = data as JobItem[]);
    this.role = this.editService.getRole();


    this.user = this.formBuilder.group({
      email: [this.auxUser.email],
      firstname: [this.auxUser.firstname],
      lastname: [this.auxUser.lastname],
      birthDate: [this.auxUser.birthDate],
      employmentDate: [this.auxUser.employmentDate],
      phoneNumber: [this.auxUser.phoneNumber],
      image: [this.base64Output],
      job: [this.auxUser.job],
      bio: [this.auxUser.bio]
    });
  }


}
