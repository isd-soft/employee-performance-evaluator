import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {AdminStatsService} from "../admin-stats-service/admin-stats.service";
import {ResponseActuatorHealth} from "../admin-models/response-actuator-health.interface";
import {ResponseMetric} from "../admin-models/response-metric.interface";

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
import {Router} from "@angular/router";

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
};

export type ChartOptions2 = {
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

@Component({
  selector: 'app-admin-stats',
  templateUrl: './admin-stats.component.html',
  styleUrls: ['./admin-stats.component.css']
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

  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions!: Partial<ChartOptions> | any;
  @ViewChild("chart2") chart2!: ChartComponent;
  public chartOptions2!: Partial<ChartOptions> | any;

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
      this.setChartOptions();
      this.setChartOptions2();
    }, error => {
      this.count500 = 0;
      this.setChartOptions();
      this.setChartOptions2();
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
    })
  }

  ngAfterViewInit(): void {

  }

  setChartOptions(): void {
    this.chartOptions = {
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

  setChartOptions2(): void {
    this.chartOptions2 = {
      title: {
        text: "HTTP RESPONSES"
      },
      series: [
        {
          name: "Statuses",
          data: [this.count200, this.count400, this.count404, this.count500],
          colors: ["#42d14b", "#1d5dda", "#FF8C00", "#DC143C"],
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

  reload() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/stats']);
  }
}

