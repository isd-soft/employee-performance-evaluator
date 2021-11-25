import {AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AssessmentView} from "../assessments-models/assessment-short-view.interface";
import {AssessmentsService} from "../assessments-services/assessments.service";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {AssessmentInformation} from "../assessments-models/AssessmentInformation";

@Component({
  selector: 'app-assessments',
  templateUrl: './assessments.component.html',
  styleUrls: ['./assessments.component.css'],
  encapsulation: ViewEncapsulation.None
})

export class AssessmentsComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['title', 'position', 'startDate', 'status', 'buttons'];
  columnAssessmentInformation: string[] = ['assessmentTitle', 'performedAction', 'performedTime', 'performedOnUser', 'performedByUser'];

  dataSource!: MatTableDataSource<AssessmentView>;
  dataSourceInformation!: MatTableDataSource<AssessmentInformation>

  assessments!: AssessmentView[];
  assessmentInformation!: AssessmentInformation[];

  jwtUser : JwtUser;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private assessmentsService: AssessmentsService,
              private jwtService: JwtService) {

    this.jwtUser = jwtService.getJwtUser();
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.assessmentsService.getAllAssessmentsByUserId(this.jwtUser.id).subscribe(
      data => {

        this.assessments = data as AssessmentView[];
        this.dataSource = new MatTableDataSource<AssessmentView>(this.assessments);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    )

    this.assessmentsService.getAssessmentInformation().subscribe(
      data => {
        this.assessmentInformation = data as AssessmentInformation[];
        this.dataSourceInformation = new MatTableDataSource<AssessmentInformation>(this.assessmentInformation);
        this.dataSourceInformation.paginator = this.paginator
        this.dataSourceInformation.sort = this.sort;
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

  applyFilter2(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }

  }
}
