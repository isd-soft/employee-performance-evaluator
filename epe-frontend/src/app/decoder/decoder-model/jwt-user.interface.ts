export interface JwtUser {
    id: string;
    issDate: Date;
    expDate: Date;
    firstname: string;
    lastname: string;
    email: string;
    role: string;
}
