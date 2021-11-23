import { DatePipe } from "@angular/common";
import { Injectable } from "@angular/core";
import { LoginRequest } from "../home-models/login-request.interface"
import { NewUser } from "../home-models/new-user.interface";
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

    createRegisterUserFromNewUser(newUser: NewUser): RegisterRequest {

        let datePipe = new DatePipe('en-US');
        let registerUser: RegisterRequest = {
            email: newUser.email,
            firstname: newUser.firstname,
            lastname: newUser.lastname,
            birthDate: datePipe.transform(newUser.birthDate, 'dd-MM-yyyy') as string,
            employmentDate: datePipe.transform(newUser.employmentDate, 'dd-MM-yyyy') as string,
            phoneNumber: newUser.phoneNumber,
            job: newUser.job,
            bio: '',
            password: newUser.password,
        }

        return registerUser;
    }

    createEmptyNewUser(): NewUser {

        let newUser: NewUser = {
            email: '',
            firstname: '',
            lastname: '',
            birthDate: new Date(),
            employmentDate: new Date(),
            phoneNumber: '',
            job: '',
            password: '',
            confirmPassword: '',
        }

        return newUser;
    }

    createLoginUserFromRegisterUser(user: RegisterRequest): LoginRequest {

        let loginUser: LoginRequest = {
            email: user.email,
            password: user.password
        }

        return loginUser;
    }
}
