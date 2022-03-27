import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from "@angular/common/http";
import {sendMessageToMaster} from "@angular/compiler-cli/ngcc/src/execution/cluster/utils";
import {stringify} from "@angular/compiler/src/util";

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
    let authorizationParam = new HttpParams();
    authorizationParam = authorizationParam.append("authorization", this.secureToken);
    return this.http.get('http://localhost:8080/api/private', {
      observe: 'response',
      headers: {
        authorization: this.secureToken
      }
    })
  }


}
