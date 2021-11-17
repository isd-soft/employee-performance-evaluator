import { Component, Injectable, OnInit } from '@angular/core';
import { JobItem } from '../edit-models/job-item.interface';
import { EditFiller } from './edit.filler';
import { UpdateRequest } from '../edit-models/update-request.interface';
import { User } from '../edit-models/user.interface';
import { JwtService } from 'src/app/decoder/decoder-service/jwt.service';
import { EditService } from '../edit-service/edit.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
@Injectable({providedIn : 'root'})
export class EditComponent implements OnInit {

  user? : User;
  updateUser? : UpdateRequest;

  jobList? : JobItem[];

  constructor(private jwtService: JwtService,
              private editService: EditService,
              private filler: EditFiller,
              private router: Router) { 
    //this.user = this.filler.loadUserToEdit();
    //this.updateUser = this.filler.updateUser(this.user);
    this.editService.getJobList().subscribe(data =>
    this.jobList = data as JobItem[]);
  }

  update() {
    if (this.user) {
      let user: UpdateRequest = this.filler.updateUserToEditFromUser(this.user);
      this.editService.update(user);
    }

  }

  ngOnInit(): void {
  }

}
