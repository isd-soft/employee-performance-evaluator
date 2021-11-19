import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AssessmentView} from "../assessments-models/assessment-view.interface";
import {AssessmentsService} from "../assessments-service/assessments.service";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";

@Component({
  selector: 'app-assessments-user',
  templateUrl: './assessments-user.component.html',
  styleUrls: ['./assessments-user.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})

export class AssessmentsUserComponent implements OnInit, AfterViewInit {

  displayedColumnsFirstTable: string[] = ['title', 'position', 'startDate', 'status', 'buttons'];
  displayedColumnsSecondTable: string[] = ['title', 'startDate', 'endDate', 'score', 'buttons'];
  firstDataSource!: MatTableDataSource<AssessmentView>;
  secondDataSource!: MatTableDataSource<AssessmentView>;

  activeAssessments!: AssessmentView[];
  finishedAssessments!: AssessmentView[];
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
    this.assessmentsService.getAllAssessmentsByUserAndStatus(this.jwtUser.id, 'active').subscribe(
      data => {
        this.activeAssessments = data as AssessmentView[];
        this.firstDataSource = new MatTableDataSource<AssessmentView>(this.activeAssessments);
        this.firstDataSource.paginator = this.paginator;
        this.firstDataSource.sort = this.sort;
      }
    );

    this.assessmentsService.getAllAssessmentsByUserAndStatus(this.jwtUser.id, 'FINISHED').subscribe(
      data => {
        this.finishedAssessments = data as AssessmentView[];
        this.secondDataSource = new MatTableDataSource<AssessmentView>(this.finishedAssessments);
        this.secondDataSource.paginator = this.paginator;
        this.secondDataSource.sort = this.sort;
      }
    );

  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.firstDataSource.filter = filterValue.trim().toLowerCase();

    if (this.firstDataSource.paginator) {
      this.firstDataSource.paginator.firstPage();
    }

  }
}
