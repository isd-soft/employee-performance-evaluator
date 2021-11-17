import { DatePipe } from "@angular/common";
import { Injectable } from "@angular/core";
import { User } from "../edit-models/user.interface";
import { UpdateRequest } from "../edit-models/update-request.interface";

@Injectable()
export class EditFiller{


    // createRegisterUserFromNewUser(user: User): UpdateRequest {

    //     let datePipe = new DatePipe('en-US');
    //     let updateUser: UpdateRequest = {
    //         email: user.email,
    //         firstname: user.firstname,
    //         lastname: user.lastname,
    //         birthDate: datePipe.transform(user.birthDate, 'dd-MM-yyyy') as string,
    //         employmentDate: datePipe.transform(user.employmentDate, 'dd-MM-yyyy') as string,
    //         phoneNumber: user.phoneNumber,
    //         job: user.job,
    //         bio: user.bio,
    //         imageFile: user.imageFile
    //     }

    //     return updateUser;
    // }

    // loadUserToEdit(): User {

    //     let user: User = {
    //         email: '',
    //         firstname: '',
    //         lastname: '',
    //         birthDate: new Date(),
    //         employmentDate: new Date(),
    //         phoneNumber: '',
    //         job: '',
    //     }   

    //     return user;
    // }

}