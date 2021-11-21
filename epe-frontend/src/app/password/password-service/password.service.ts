import {HttpClient} from "@angular/common/http";
import {JwtService} from "../../decoder/decoder-service/jwt.service";
import {JwtUser} from "../../decoder/decoder-model/jwt-user.interface";
import {Injectable} from "@angular/core";
import {PasswordTemplate} from "../password-model/password.interface";

@Injectable({providedIn : 'root'})
export class PasswordService {

  url: string = 'api-server/api/users';

  jwtUser?: JwtUser;
  id? : string

  constructor(private http: HttpClient, private jwtService: JwtService) {
    this.jwtUser = this.jwtService.getJwtUser();
    if(this.jwtUser)
      this.id = this.jwtUser.id;
  }

  changePassword(passwordDto: PasswordTemplate) {
    return this.http.put(this.url + '/' + this.id + '/password',)
  }
}
