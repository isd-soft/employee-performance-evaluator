import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {AssessmentView} from "../assessments-models/assessment-view.interface";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AssessmentsService} from "../assessments-service/assessments.service";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";

@Component({
  selector: 'app-assessments-history-user',
  templateUrl: './assessments-history-user.component.html',
  styleUrls: ['./assessments-history-user.component.css']
})
export class AssessmentsHistoryUserComponent implements OnInit {

  displayedColumns: string[] = ['title', 'startDate', 'endDate', 'score', 'buttons'];
  dataSource!: MatTableDataSource<AssessmentView>;

  finishedAssessments!: AssessmentView[];
  jwtUser: JwtUser;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private assessmentsService: AssessmentsService,
              private jwtService: JwtService) {

    this.jwtUser = jwtService.getJwtUser();
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.assessmentsService.getAllAssessmentsByUserAndStatus(this.jwtUser.id, 'FINISHED').subscribe(
      data => {
        this.finishedAssessments = data as AssessmentView[];
        this.dataSource = new MatTableDataSource<AssessmentView>(this.finishedAssessments);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    );

  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
