import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {User} from "../../assessments/assessments-models/User";
import {AssessmentView} from "../../assessments/assessments-models/assessment-view.interface";
import {AssessmentTemplate} from "../../assessment/assessment-model/Assessment-template";
import {AdminBoardService} from "../admin-board-service/AdminBoardService";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {AssessmentsViewComponent} from "../../assessments/assessments-view/assessments-view.component";
import {MatDialog} from "@angular/material/dialog";
import { MatTableModule } from '@angular/material/table'
import {DeleteAssessmentWarningComponent} from "../delete-assessment-warning/delete-assessment-warning.component";

@Component({
  selector: 'app-admin-board',
  templateUrl: './admin-board.component.html',
  styleUrls: ['./admin-board.component.css']
})
export class AdminBoardComponent implements OnInit, AfterViewInit {

  dataSource!: MatTableDataSource<AssessmentView>;
  assessments!: AssessmentView[];
  displayedColumns: string[] = ['title', 'evaluatedUser', 'startDate', 'status', 'buttons'];

  jwtUser: User;


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private jwtService: JwtService, private adminBoardService: AdminBoardService, public dialog: MatDialog) {
    this.jwtUser = jwtService.getJwtUser();
  }



  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.adminBoardService.getAllAssessments().subscribe(
      data => {
        this.assessments = data as AssessmentView[];
        this.dataSource = new MatTableDataSource<AssessmentView>(this.assessments);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        console.log(this.dataSource)
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

  exportAssessmentToPdf(assessment: AssessmentView | undefined) {
    this.adminBoardService.exportAssessmentToPdf(assessment);
  }

  exportAssessmentToExcel(assessment: AssessmentView | undefined) {
    this.adminBoardService.exportAssessmentToExcel(assessment);
  }

  delete(assessmentId: string) {
    this.dialog.open(DeleteAssessmentWarningComponent, {data: assessmentId})
    // this.adminBoardService.deleteAssessment
  }
}
