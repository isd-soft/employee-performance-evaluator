import {HealthComponent} from "./health-component.interface";

export interface ResponseActuatorHealth {
  status: string;
  components: HealthComponent;
}
