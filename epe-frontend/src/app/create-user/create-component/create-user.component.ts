import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {NewUser} from "../../components/usersview/userview-models/NewUser";
import {JobItem} from "../../components/home/home-models/job-item.interface";
import {DatePipe} from "@angular/common";
import {CreateUserService} from "../../components/usersview/userview-services/create-user.service";
import {HomeService} from "../../components/home/home-service/home.service";

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  newUser?: FormGroup;

  userDto?: NewUser;

  jobList?: JobItem[];

  constructor(private createService: CreateUserService,
              private homeService: HomeService,
              private formBuilder: FormBuilder) {
    this.homeService.getJobList().subscribe(data => {
      this.jobList = data as JobItem[];
    })

    // @ts-ignore
    this.userDto = {
      email: '',
      firstname: '',
      lastname: '',
      birthDate: '',
      job : '',
      employmentDate: '',
      phoneNumber: '',
      password: 'password',
      bio: 'Here goes your bio'
    }
  }

  ngOnInit(): void {
    this.newUser = this.formBuilder.group({
      email: [],
      firstname: [],
      lastname: [],
      birthDate: [],
      employmentDate: [],
      phoneNumber: [],
      job: [],
      password: [this.userDto?.password],
      bio: [this.userDto?.bio]
    })
  }

  createUser() {
    let datePipe = new DatePipe('en-US');
    // @ts-ignore
    this.newUser?.value.birthDate = datePipe.transform(this.newUser?.value.birthDate, 'dd-MM-yyyy') as string;
    // @ts-ignore
    this.newUser?.value.employmentDate = datePipe.transform(this.newUser?.value.employmentDate, 'dd-MM-yyyy') as string;
    this.createService.createUser(this.newUser?.value);
  }

}
