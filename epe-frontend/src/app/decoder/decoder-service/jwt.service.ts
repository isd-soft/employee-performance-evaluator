import { JwtUser } from './../decoder-model/jwt-user.interface';
import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  jwtUser?: JwtUser

  decodeJwt(): any {
    
    // test token
    let token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE2MzY3MDQ1NTEsImV4cCI6MTY2ODI0MDU1NywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjoiUk9MRV9VU0VSIn0.0xmOdqQDiFfawN0lcwaiwRBjn0XudOAFdURHA9vFgKc'

    let decodedToken: any

    try{
      decodedToken = jwt_decode(token)
      
      if(decodedToken.exp && decodedToken.Email && decodedToken.Role) {
        this.jwtUser = {
          expDate: decodedToken.exp,
          email: decodedToken.Email,
          role: decodedToken.Role
        }

        return this.jwtUser
      }

      return null;
    }
    catch(Error){
      return null;
    }
  }

  storeJWT(token: string) {
    localStorage.setItem ('JWT_TOKEN', token)
  }

  removeJWT() {
    if(localStorage.getItem('JWT_TOKEN')) {
      localStorage.removeItem('JWT_TOKEN')
      console.log('yes ..')
    }
  }

  validateJWT(): boolean {
    return true;
  }

  
}
