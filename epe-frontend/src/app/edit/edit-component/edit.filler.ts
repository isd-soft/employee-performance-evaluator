import { DatePipe } from "@angular/common";
import { Injectable } from "@angular/core";
import { User } from "../edit-models/user.interface";
import { UpdateRequest } from "../edit-models/update-request.interface";
import { noUndefined } from "@angular/compiler/src/util";
import {isFile} from "@angular/compiler-cli/src/ngtsc/file_system/testing/src/mock_file_system";

@Injectable({providedIn : "root"})
export class EditFiller{


    updateUserFromTemplate(user: User): UpdateRequest {

        console.log(user)

        let datePipe = new DatePipe('en-US');
        let updateUser: UpdateRequest = {
            image :user.image ,
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

    createUserToEdit(): UpdateRequest {

        let user: UpdateRequest = {
            image: '',
            email: '',
            firstname: '',
            lastname: '',
            birthDate: '',
            employmentDate: '',
            phoneNumber: '',
            job: '',
            bio: ''
        }

        return user;
    }

}
