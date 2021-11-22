import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AssessmentsTemplatesService} from "../assessments-templates-services/assessments-templates.service";
import {MatDialog} from "@angular/material/dialog";
import {AssessmentTemplateViewComponent} from "../assessment-template-view-component/assessment-template-view.component";
import {AssessmentTemplateView} from "../assessments-templates-models/assessment-template-view.interface";

@Component({
  selector: 'app-assessments-templates',
  templateUrl: './assessments-templates.component.html',
  styleUrls: ['./assessments-templates.component.css']
})
export class AssessmentsTemplatesComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['title', 'jobTitle', 'buttons'];
  dataSource!: MatTableDataSource<AssessmentTemplateView>;

  assessmentsTemplates!: AssessmentTemplateView[];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private assessmentsTemplatesService: AssessmentsTemplatesService,
              public dialog: MatDialog)
  { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.assessmentsTemplatesService.getAllAssessmentsTemplates().subscribe(
      data => {

        this.assessmentsTemplates = data as AssessmentTemplateView[];
        this.dataSource = new MatTableDataSource<AssessmentTemplateView>(this.assessmentsTemplates);
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
    this.dialog.open(AssessmentTemplateViewComponent, {
      height: '90%',
      width: '90%',
      data : this.assessmentsTemplates[id]
    });
  }

}
