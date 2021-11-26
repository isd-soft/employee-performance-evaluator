import { UserviewsServices } from './../userview-services/users-view.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TeamsComponent } from '../../teams/team-component/teams.component';

@Component({
  selector: 'app-user-delete',
  templateUrl: './user-delete.component.html',
  styleUrls: ['./user-delete.component.css']
})
export class UserDeleteComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public userID: string,
              public dialogRef: MatDialogRef<TeamsComponent>,
              private service: UserviewsServices) {
  }

  delete() {
        this.service.deleteUser(this.userID);
        this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  } 

}
