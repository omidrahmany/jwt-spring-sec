import {Component, OnInit} from '@angular/core';
import {AuthService} from "../servic/auth.service";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-private-zone',
  templateUrl: './private-zone.component.html',
  styleUrls: ['./private-zone.component.css']
})
export class PrivateZoneComponent implements OnInit {
  get errMsg(): string {
    return this._errMsg;
  }

  set errMsg(value: string) {
    this._errMsg = value;
  }
  get responseObj() {
    return this._responseObj;
  }

  get errorObj(): Error {
    return this._errorObj;
  }

  constructor(private authService: AuthService) {
    this._isAllowedToShowPrivateContent = false
    this.isErrMode = false;
  }

  private _isErrMode: boolean
  private _isAllowedToShowPrivateContent: boolean;
  private _errorObj: Error;
  // @ts-ignore
  private _responseObj;
  private _errMsg: string = '';

  get isErrMode(): boolean {
    return this._isErrMode;
  }

  set isErrMode(value: boolean) {
    this._isErrMode = value;
  }


  get isAllowedToShowPrivateContent(): boolean {
    return this._isAllowedToShowPrivateContent;
  }

  set isAllowedToShowPrivateContent(value: boolean) {
    this._isAllowedToShowPrivateContent = value;
  }

  ngOnInit(): void {
  }

  onShowPrivateContent() {
    this.authService.showPrivateContent()
      .subscribe({
        next: value => {
          if (value.headers.get("Exception-Code") === "UNAUTHENTICATED") {
            this.isErrMode = true;
            this.isAllowedToShowPrivateContent = false;
            console.log('err response: ');
            console.log(value);
            if (value.headers.get("Exception-Code")) {
              this._errMsg = <string>value.headers.get("Exception-Code");
            }
          } else {
            this.isErrMode = false;
            this.isAllowedToShowPrivateContent = true;
            console.log('response: ');
            console.log(value);
          }
          this._responseObj = value;

        },
        error: err => {
          this.isErrMode = true;
          this._errorObj = err;
          console.log('error: ')
          console.log(err)
        }
      })
  }
}
