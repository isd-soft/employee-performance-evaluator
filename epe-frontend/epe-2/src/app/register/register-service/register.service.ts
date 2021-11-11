import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  url: string = 'http://localhost:8100/clients'

  constructor(private http: HttpClient) { }
}
