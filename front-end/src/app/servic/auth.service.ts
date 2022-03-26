import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ThisReceiver} from "@angular/compiler";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  secureToken: string = '';

  constructor(private http: HttpClient) {
  }

  login(data: { username: string, password: string }) {
    let searchParams = new HttpParams();
    searchParams = searchParams
      .append("username", data.username)
      .append("password", data.password);
    this.http.get('http://localhost:8080/api/authenticate', {
      params: searchParams,
      observe: 'response'
    })
      .subscribe({
        next: value => {




          console.log("response: ");
          console.log(value);
          if (value.headers.get('authorization') !== null) {
            // @ts-ignore
            // this.secureToken = value.headers.headers.get('authorization').replace('Bearer ', '');
            console.log(value.headers.get('authorization'));


          }
          console.log('my token: '.concat(this.secureToken));
          console.log('headers: ');
          console.log(value.headers);
        }
      })
  }


}
