import { JwtUser } from './../decoder-model/jwt-user.interface';
import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  getJwtUser(): any {

    let jwtUser: JwtUser;
    let token: any = localStorage.getItem('JWT_TOKEN');

    if(token) {
      try{

        let decodedToken: any = jwt_decode(token);

        if(this.validateDecodedJwtToken(decodedToken)) {
          jwtUser = {
            id: decodedToken.id,
            issDate: decodedToken.iat,
            expDate: decodedToken.exp,
            firstname: decodedToken.firstname,
            lastname: decodedToken.lastname,
            email: decodedToken.sub,
            role: decodedToken.role
          }

          return jwtUser;
        }
        else {
          localStorage.removeItem('JWT_TOKEN');
        }
      }
      catch(Error){ }
    }

    return null;
  }

  storeJWT(token: string) {

    localStorage.setItem ('JWT_TOKEN', token);
  }

  removeJWT() {

    if(localStorage.getItem('JWT_TOKEN')) {
      localStorage.removeItem('JWT_TOKEN');
    }
  }

  validateDecodedJwtToken(decodedToken: any): boolean {

    if(decodedToken.id)
      if(decodedToken.sub)
        if(decodedToken.firstname)
          if(decodedToken.lastname)
            if(decodedToken.role)
              if(decodedToken.iat)
                if(decodedToken.exp)
                  return true;

    return false;
  }
}
