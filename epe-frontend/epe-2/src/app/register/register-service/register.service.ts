import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Job } from '../register-models/job';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  url: string = 'http://localhost:8100/clients'

  jobList: Job[] = [
    {
      name: 'Developer'
    },
    {
      name: 'Tester'
    },
    {
      name: 'Manager'
    }
  ]

  constructor(private http: HttpClient) { }

  getJobs() {
    return this.jobList;
  }
}
