import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {AssessmentView} from "../assessments-models/assessment-view.interface";
import {JwtUser} from "../../../decoder/decoder-model/jwt-user.interface";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AssessmentsService} from "../assessments-services/assessments.service";
import {JwtService} from "../../../decoder/decoder-service/jwt.service";
import {MatDialog} from "@angular/material/dialog";
import {AssessmentsViewComponent} from "../assessments-view/assessments-view.component";
import {AdminBoardService} from "../../admin-board/admin-board-service/AdminBoardService";

@Component({
  selector: 'app-assessments-history',
  templateUrl: './assessments-history.component.html',
  styleUrls: ['./assessments-history.component.css']
})
export class AssessmentsHistoryComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['title', 'evaluatedUser', 'endDate', 'status', 'buttons'];
  dataSource!: MatTableDataSource<AssessmentView>;

  assessments!: AssessmentView[];
  jwtUser : JwtUser;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private assessmentsService: AssessmentsService,
              private jwtService: JwtService,
              public dialog: MatDialog,
              private adminBoardService: AdminBoardService) {

    this.jwtUser = jwtService.getJwtUser();
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.assessmentsService.getAllAssessmentsByUserIdAndStatus(this.jwtUser.id, 'INACTIVE').subscribe(
      data => {
        this.assessments = data as AssessmentView[];
        this.dataSource = new MatTableDataSource<AssessmentView>(this.assessments);
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

}
