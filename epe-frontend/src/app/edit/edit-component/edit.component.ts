import {Component, Injectable, OnInit} from '@angular/core';
import {JobItem} from '../edit-models/job-item.interface';
import {EditFiller} from './edit.filler';
import {User} from '../edit-models/user.interface';
import {JwtService} from 'src/app/decoder/decoder-service/jwt.service';
import {EditService} from '../edit-service/edit.service';
import {Router} from '@angular/router';
import {DatePipe} from "@angular/common";
import {Observable, ReplaySubject} from "rxjs";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
@Injectable({providedIn: 'root'})
export class EditComponent implements OnInit {

  user?: User

  jobList?: JobItem[];

  auxUser?: User;

  errorMessage? : string;

  constructor(private jwtService: JwtService,
              private editService: EditService,
              private filler: EditFiller,
              private router: Router) {
    this.editService.getUser().subscribe(data =>
    this.auxUser = data as User);
    this.editService.getJobList().subscribe(data =>
      this.jobList = data as JobItem[]);
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
    let datePipe = new DatePipe('en-US');
    // @ts-ignore
    this.auxUser.birthDate = datePipe.transform(this.auxUser.birthDate, 'dd-MM-yyyy') as string;
    // @ts-ignore
    this.auxUser.employmentDate = datePipe.transform(this.auxUser.employmentDate, 'dd-MM-yyyy') as string;
    // @ts-ignore
    this.auxUser.image = this.base64Output;
    this.editService.update(this.auxUser).subscribe(data => {
    }, error => {
       this.errorMessage = error.error.title;})

  }

  ngOnInit(): void {
  }


}
