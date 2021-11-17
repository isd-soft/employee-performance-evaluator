import { DatePipe } from "@angular/common";
import { Injectable } from "@angular/core";
import { User } from "../edit-models/user.interface";
import { UpdateRequest } from "../edit-models/update-request.interface";

@Injectable({providedIn : "root"})
export class EditFiller{


    updateUserToEditFromUser(user: User): UpdateRequest {

        let datePipe = new DatePipe('en-US');
        let updateUser: UpdateRequest = {
            imageFile: user.imageFile,
            email: user.email,
            firstname: user.firstname,
            lastname: user.lastname,
            birthDate: datePipe.transform(user.birthDate, 'dd-MM-yyyy') as string,
            employmentDate: datePipe.transform(user.employmentDate, 'dd-MM-yyyy') as string,
            phoneNumber: user.phoneNumber,
            job: user.job,
            bio: user.bio
        }

        return updateUser;
    }

    /*loadUserToEdit(): User {

        let user: User = {
            imageFile: '',
            email: '',
            firstname: '',
            lastname: '',
            birthDate: new Date(),
            employmentDate: new Date(),
            phoneNumber: '',
            job: '',
            bio: ''
        }   

        return user;
    }*/

}