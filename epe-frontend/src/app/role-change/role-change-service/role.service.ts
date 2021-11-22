import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class RoleService {

  url: string = 'api-server/api/roles';

  constructor(private http: HttpClient) {
  }

  getRoles() {
    return this.http.get(this.url)
  }

}
