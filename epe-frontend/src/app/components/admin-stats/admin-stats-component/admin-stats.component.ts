import {AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {AdminStatsService} from "../admin-stats-service/admin-stats.service";
import {ResponseActuatorHealth} from "../admin-models/response-actuator-health.interface";
import {ResponseMetric} from "../admin-models/response-metric.interface";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {ResponseHttpTraces} from "../admin-models/response-http-traces.interface";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {HttpTrace} from "../admin-models/http-trace.interface";
import {
  ApexNonAxisChartSeries,
  ApexResponsive,
  ApexChart,
  ChartComponent,
  ApexAxisChartSeries,
  ApexDataLabels,
  ApexPlotOptions,
  ApexXAxis,
  ApexYAxis,
  ApexFill,
  ApexTooltip,
  ApexStroke,
  ApexLegend
} from "ng-apexcharts";
import {ChartOptions} from "chart.js";
import {AssessmentView} from "../../assessments/assessments-models/assessment-view.interface";
import {UserView} from "../../users/user-models/user-view.interface";
import {NewUsersCount} from "../admin-models/new-users-count.interface";
import {NewAssessmentsCount} from "../admin-models/new-assessments-count.interface";
import {Count} from "../admin-models/count.interface";

export type ResponsesChartOptions1 = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
};

export type ResponsesChartOptions2 = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  yaxis: ApexYAxis;
  xaxis: ApexXAxis;
  fill: ApexFill;
  tooltip: ApexTooltip;
  stroke: ApexStroke;
  legend: ApexLegend;
};

export type UsersChartOptions1 = {
  series: ApexNonAxisChartSeries;
  colors: string[];
  chart: ApexChart;
  labels: string[];
  plotOptions: ApexPlotOptions;
}


export type UsersChartOptions2 = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  colors: string[];
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  yaxis: ApexYAxis;
  xaxis: ApexXAxis;
  fill: ApexFill;
  tooltip: ApexTooltip;
  stroke: ApexStroke;
  legend: ApexLegend;
};

@Component({
  selector: 'app-admin-stats',
  templateUrl: './admin-stats.component.html',
  styleUrls: ['./admin-stats.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AdminStatsComponent implements AfterViewInit {

  count200!: number;
  count400!: number;
  count404!: number;
  count500!: number;
  serverStatus!: string;
  DBName!: string;
  DBStatus!: string;
  diskUsage!: number;
  diskTotalSpace!: number;
  processorsCount!: number;
  uptime!: number;

  assessmentsCount!: number;
  usersCount!: number;
  buddiesCount!: number;
  teamLeadersCount!: number;
  newUsersCount!: NewUsersCount;
  newAssessmentsCount!: NewAssessmentsCount;

  @ViewChild("chart") responsesChart1!: ChartComponent;
  public responsesChartOptions1!: Partial<ResponsesChartOptions1> | any;
  @ViewChild("chart") responsesChart2!: ChartComponent;
  public responsesChartOptions2!: Partial<ResponsesChartOptions2> | any;

  @ViewChild("chart") assessmentsChart1!: ChartComponent;
  public assessmentsChartOptions1!: Partial<UsersChartOptions1> | any;
  @ViewChild("chart") assessmentsChart2!: ChartComponent;
  public assessmentsChartOptions2!: Partial<UsersChartOptions2> | any;

  @ViewChild("chart") usersChart1!: ChartComponent;
  public usersChartOptions1!: Partial<UsersChartOptions1> | any;
  @ViewChild("chart") usersChart2!: ChartComponent;
  public usersChartOptions2!: Partial<UsersChartOptions2> | any;

  @ViewChild("chart") buddiesChart!: ChartComponent;
  public buddiesChartOptions!: Partial<UsersChartOptions1> | any;

  @ViewChild("chart") teamLeadersChart!: ChartComponent;
  public teamLeadersChartOptions!: Partial<UsersChartOptions1> | any;

  displayedColumns: string[] = ['timestamp', 'method', 'timeTaken', 'status', 'uri'];
  dataSource!: MatTableDataSource<HttpTrace>;
  httpTraceResponse!: ResponseHttpTraces;
  private paginator!: MatPaginator;
  private sort!: MatSort;

  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    this.setDataSourceAttributes();
  }

  setDataSourceAttributes() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  constructor(private adminStatsService: AdminStatsService,
              private router: Router) {

  }

  ngOnInit(): void {
    this.adminStatsService.getResponseStatusesCount200().subscribe(response => {
      let responseStatuses: ResponseMetric;
      responseStatuses = response as ResponseMetric;
      responseStatuses.measurements.forEach(measurement => {
        if (measurement.statistic == 'COUNT') {
          this.count200 = measurement.value;
        }
      });
    }, error => {
      this.count200 = 0;
    });

    this.adminStatsService.getResponseStatusesCount404().subscribe(response => {
      let responseStatuses: ResponseMetric;
      responseStatuses = response as ResponseMetric;
      responseStatuses.measurements.forEach(measurement => {
        if (measurement.statistic == 'COUNT') {
          this.count404 = measurement.value;
        }
      });
    }, error => {
      this.count404 = 0;
    });

    this.adminStatsService.getResponseStatusesCount400().subscribe(response => {
      let responseStatuses: ResponseMetric;
      responseStatuses = response as ResponseMetric;
      responseStatuses.measurements.forEach(measurement => {
        if (measurement.statistic == 'COUNT') {
          this.count400 = measurement.value;
        }
      });
    }, error => {
      this.count400 = 0;
    });

    this.adminStatsService.getResponseStatusesCount500().subscribe(response => {
      let responseStatuses: ResponseMetric;
      responseStatuses = response as ResponseMetric;
      responseStatuses.measurements.forEach(measurement => {
        if (measurement.statistic == 'COUNT') {
          this.count500 = measurement.value;
        }
      });
      this.setResponsesChartOptions1();
      this.setResponsesChartOptions2();
    }, error => {
      this.count500 = 0;
      this.setResponsesChartOptions1();
      this.setResponsesChartOptions2();
    });



    this.adminStatsService.getHealth().subscribe(response => {
      let actuatorHealth = response as ResponseActuatorHealth;
      this.serverStatus = actuatorHealth.status;
      this.DBName = actuatorHealth.components.db.details.database;
      this.DBStatus = actuatorHealth.components.db.status;
      this.diskTotalSpace = actuatorHealth.components.diskSpace.details.total;
      this.diskUsage = this.diskTotalSpace - actuatorHealth.components.diskSpace.details.free;

      this.diskTotalSpace = this.diskTotalSpace / 1073741824;
      this.diskUsage = this.diskUsage / 1073741824;

    }, error => {
      this.serverStatus = 'DOWN';
      this.DBName = "Unknown";
      this.DBStatus = "Unknown";
      this.diskTotalSpace = 0;
      this.diskUsage = 0;
    });

    this.adminStatsService.getProcessorsCount().subscribe(response => {
      let processorCountResponse = response as ResponseMetric;
      processorCountResponse.measurements.forEach(measurement => {
        if (measurement.statistic == 'VALUE') {
          this.processorsCount = measurement.value;
        }
      });
    }, error => {
      this.processorsCount = 0;
    });

    this.adminStatsService.getProcessUptime().subscribe(response => {
      let processUptimeResponse = response as ResponseMetric;

      processUptimeResponse.measurements.forEach(measurement => {
        if (measurement.statistic == 'VALUE') {
          this.uptime = measurement.value;
        }
      });
    }, error => {
      this.uptime = 0;
    });

    this.adminStatsService.getHttpTraces().subscribe(response => {
      this.httpTraceResponse = response as ResponseHttpTraces;
      this.httpTraceResponse.traces.forEach(trace => {
        trace.method = trace.request.method;
        trace.uri = trace.request.uri;
        trace.status = trace.response.status;
      })
      this.dataSource = new MatTableDataSource<HttpTrace>(this.httpTraceResponse.traces);
      setTimeout(() => this.dataSource.paginator = this.paginator);
      this.dataSource.sort = this.sort;
    });

    this.adminStatsService.getAssessmentsCount().subscribe(response => {
      let countResponse = response as Count;
      this.assessmentsCount = countResponse.count;
      this.setAssessmentsChartOptions1();
    }, error => {
      this.assessmentsCount = 0;
      this.setAssessmentsChartOptions1();
    });

    this.adminStatsService.getUsersCount().subscribe(response => {
      let countResponse = response as Count;
      this.usersCount = countResponse.count;
      this.setUsersChartOptions1();
    }, error => {
      this.usersCount = 0;
      this.setUsersChartOptions1();
    });

    this.adminStatsService.getNewUsersThisYearCount().subscribe(response => {
      this.newUsersCount = response as NewUsersCount;
      this.setUsersChartOptions2();
    }, error => {
      this.newUsersCount.months = [0,0,0,0,0,0,0,0,0,0,0,0];
      this.setUsersChartOptions2();
    });

    this.adminStatsService.getNewAssessmentsThisYearCount().subscribe(response => {
      this.newAssessmentsCount = response as NewAssessmentsCount;
      this.setAssessmentsChartOptions2();
    }, error => {
      this.newAssessmentsCount.months = [0,0,0,0,0,0,0,0,0,0,0,0];
      this.setAssessmentsChartOptions2();
    });

    this.adminStatsService.getBuddiesCount().subscribe(response => {
      let countResponse = response as Count;
      this.buddiesCount = countResponse.count;
      this.setBuddiesChartOptions();
    }, error => {
      this.buddiesCount = 0;
      this.setBuddiesChartOptions();
    });

    this.adminStatsService.getTeamLeadersCount().subscribe(response => {
      let countResponse = response as Count;
      console.log(countResponse);
      this.teamLeadersCount = countResponse.count;
      this.setTeamLeadersChartOptions();
    }, error => {
      this.teamLeadersCount = 0;
      this.setTeamLeadersChartOptions();
    })
  }

  ngAfterViewInit(): void {

  }

  setResponsesChartOptions1(): void {
    this.responsesChartOptions1 = {
      title: {
        text: "HTTP RESPONSES"
      },
      colors: ["#42d14b", "#1d5dda", "#FF8C00", "#DC143C"],
      series: [this.count200, this.count404, this.count400, this.count500],
      chart: {
        width: 380,
        type: "pie"
      },
      labels: ["200", "400", "404", "500"],
      responsive: [

      ]
    };
  }

  setResponsesChartOptions2(): void {
    this.responsesChartOptions2 = {
      title: {
        text: "HTTP RESPONSES"
      },
      series: [
        {
          name: "Statuses",
          data: [this.count200, this.count400, this.count404, this.count500],
        }
      ],
      chart: {
        type: "bar",
        height: 350
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: "55%",
          endingShape: "rounded"
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        show: true,
        width: 2,
        colors: ["transparent"]
      },
      xaxis: {
        categories: [
          '200',
          '400',
          '404',
          '500'
        ]
      },
      yaxis: {
        title: {
          text: "responses"
        }
      },
      fill: {
        opacity: 1
      },
      tooltip: {
        y: {
          formatter: function(val: any) {
            return val + " responses";
          }
        }
      }
    };
  }

  setAssessmentsChartOptions1(): void {
    this.assessmentsChartOptions1 = {
      series: [this.assessmentsCount],
      chart: {
        height: 350,
        type: "radialBar"
      },
      plotOptions: {
        radialBar: {
          track: {
            opacity: 1
          },
          dataLabels: {
            name: {
              offsetY: -10
            },
            value: {
              fontSize: '26px',
              fontWeight: 700,
              formatter: function(val: any) {
                return val;
              }
            }
          }
        }
      },
      colors: ["#7a5195"],
      labels: ["ASSESSMENTS"]
    }
  }

  setUsersChartOptions1(): void {
    this.usersChartOptions1 = {
      series: [this.usersCount],
      chart: {
        height: 350,
        type: "radialBar"
      },
      plotOptions: {
        radialBar: {
          track: {
            opacity: 1
          },
          dataLabels: {
            name: {
              offsetY: -10
            },
            value: {
              fontSize: '26px',
              fontWeight: 700,
              formatter: function(val: any) {
                return val;
              }
            }
          }
        }
      },
      colors: ["#003f5c"],
      labels: ["USERS"]
    }
  }

  setUsersChartOptions2(): void {
    this.usersChartOptions2 = {
      title: {
        text: "New users this year"
      },
      series: [
        {
          name: "Users",
          data: this.newUsersCount.months,
        }
      ],
      colors: ["#003f5c"],
      chart: {
        type: "bar",
        height: 350,
        width: 700
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: "55%",
          endingShape: "rounded"
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        show: true,
        width: 2,
        colors: ["transparent"]
      },
      xaxis: {
        categories: [
          "Jan",
          "Feb",
          "Mar",
          "Apr",
          "May",
          "Jun",
          "Jul",
          "Aug",
          "Sep",
          "Oct",
          "Nov",
          "Dec"
        ]
      },
      yaxis: {
        title: {
          text: "users"
        }
      },
      fill: {
        opacity: 1
      },
      tooltip: {
        y: {
          formatter: function(val: any) {
            return val;
          }
        }
      }
    };
  }

  setBuddiesChartOptions(): void {
    this.buddiesChartOptions = {
      series: [this.buddiesCount],
      chart: {
        height: 350,
        type: "radialBar"
      },
      plotOptions: {
        radialBar: {
          track: {
            opacity: 1
          },
          dataLabels: {
            name: {
              offsetY: -10
            },
            value: {
              fontSize: '26px',
              fontWeight: 700,
              formatter: function(val: any) {
                return val;
              }
            }
          }
        }
      },
      colors: ["#ef5675"],
      labels: ["BUDDIES"]
    }
  }

  setTeamLeadersChartOptions(): void {
    this.teamLeadersChartOptions = {
      series: [this.teamLeadersCount],
      chart: {
        height: 350,
        type: "radialBar"
      },
      plotOptions: {
        radialBar: {
          track: {
            opacity: 1
          },
          dataLabels: {
            name: {
              offsetY: -10
            },
            value: {
              fontSize: '26px',
              fontWeight: 700,
              formatter: function(val: any) {
                return val;
              }
            }
          }
        }
      },
      colors: ["#ffa600"],
      labels: ["TEAM LEADERS"]
    }
  }

  setAssessmentsChartOptions2(): void {
    this.assessmentsChartOptions2 = {
      title: {
        text: "New assessments this year"
      },
      series: [
        {
          name: "Assessments",
          data: this.newAssessmentsCount.months,
        }
      ],
      colors: ["#7a5195"],
      chart: {
        type: "bar",
        height: 350,
        width: 700
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: "55%",
          endingShape: "rounded"
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        show: true,
        width: 2,
        colors: ["transparent"]
      },
      xaxis: {
        categories: [
          "Jan",
          "Feb",
          "Mar",
          "Apr",
          "May",
          "Jun",
          "Jul",
          "Aug",
          "Sep",
          "Oct",
          "Nov",
          "Dec"
        ]
      },
      yaxis: {
        title: {
          text: "assessments"
        }
      },
      fill: {
        opacity: 1
      },
      tooltip: {
        y: {
          formatter: function(val: any) {
            return val;
          }
        }
      }
    };
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  reload() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/stats']);
  }
}

