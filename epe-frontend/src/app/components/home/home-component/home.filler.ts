import { BadResponse } from './../home-models/bad-response.interface';
import { Injectable } from "@angular/core";
import { LoginRequest } from "../home-models/login-request.interface"
import { RegisterRequest } from "../home-models/register-request.interface";

@Injectable({
    providedIn: 'root'
  })
export class HomeFiller{

    createEmptyLoginUser(): LoginRequest {

        let loginUser: LoginRequest = {
            email: '',
            password: ''
        }   

        return loginUser;
    }

    createEmptyRegisterUser(): RegisterRequest {

        let registerUser: RegisterRequest = {
            email: '',
            firstname: '',
            lastname: '',
            birthDate: '',
            employmentDate: '',
            phoneNumber: '',
            job: '',
            bio: 'new user',
            password: ''
        }

        return registerUser;
    }

    createEmptyBadResponse(): BadResponse {
    
        let badResponse: BadResponse = {
            title: '',
            details: ''
        }

        return badResponse;
    }
}