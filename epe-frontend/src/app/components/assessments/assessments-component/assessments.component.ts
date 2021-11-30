import {AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AssessmentsService} from "../assessments-services/assessments.service";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {AssessmentInformation} from "../../linefeed/line-feed-models/AssessmentInformation";
import {AssessmentsViewComponent} from "../assessments-view/assessments-view.component";
import {MatDialog} from "@angular/material/dialog";
import {AssessmentView} from "../assessments-models/assessment-view.interface";

@Component({
  selector: 'app-assessments',
  templateUrl: './assessments.component.html',
  styleUrls: ['./assessments.component.css'],
  encapsulation: ViewEncapsulation.None
})

export class AssessmentsComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['title', 'jobPosition', 'startDate', 'status', 'buttons'];
  dataSource!: MatTableDataSource<AssessmentView>;
  assessments!: AssessmentView[];
  jwtUser : JwtUser;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private assessmentsService: AssessmentsService,
              private jwtService: JwtService,
              public dialog: MatDialog) {

    this.jwtUser = jwtService.getJwtUser();
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.assessmentsService.getAllAssessmentsByUserIdAndStatus(this.jwtUser.id, 'ACTIVE').subscribe(
      data => {

        this.assessments = data as AssessmentView[];
        this.dataSource = new MatTableDataSource<AssessmentView>(this.assessments);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    )
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openDialog(id: number) {
    this.dialog.open(AssessmentsViewComponent, {
      height: '90%',
      width: '90%',
      data : this.assessments[id],
      autoFocus: false
    });
  }

}
