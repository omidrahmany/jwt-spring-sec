import {Component, OnInit} from '@angular/core';
import {AuthService} from "../servic/auth.service";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-private-zone',
  templateUrl: './private-zone.component.html',
  styleUrls: ['./private-zone.component.css']
})
export class PrivateZoneComponent implements OnInit {
  get response() {
    return this._response;
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
  private _response;

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
          this.isErrMode = false;
          this.isAllowedToShowPrivateContent = true;
          console.log('response: ');
          console.log(value);
          this._response = value;
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
