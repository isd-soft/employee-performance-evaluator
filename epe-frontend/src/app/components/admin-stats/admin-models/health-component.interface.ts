import {DatabaseComponent} from "./database-component.interface";
import {DiskComponent} from "./disk-component.interface";

export interface HealthComponent {

  db: DatabaseComponent;
  diskSpace: DiskComponent;

 }
