import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
    this._secureToken = '';
  }

  private _secureToken: string;

  get secureToken(): string {
    return this._secureToken;
  }

  set secureToken(value: string) {
    this._secureToken = value;
  }

  login(data: { username: string, password: string }) {
    let searchParams = new HttpParams();
    searchParams = searchParams
      .append("username", data.username)
      .append("password", data.password);
    return this.http.get('http://localhost:8080/api/authenticate', {
      params: searchParams,
      observe: 'response'
    })
  }

  showPrivateContent() {
    return this.http.get('http://localhost:8080/api/private', {
      observe: 'response'
     /* headers: {
        authorization: this.secureToken
      }*/
    })
  }


}
