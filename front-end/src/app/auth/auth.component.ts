import {Component, ComponentFactoryResolver, OnDestroy, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthService} from "../servic/auth.service";
import {ErrorAlertComponent} from "../error-alert/error-alert.component";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit, OnDestroy {

  errMsg: string = '';

  private subscription: Subscription;

  constructor(private authService: AuthService, private viewContainerRef: ViewContainerRef) {
    this.isLoginMode = true;
    this.isLoadingMode = false;
  }

  onSubmit(authForm: NgForm) {
    this.isLoadingMode = true
    if (this.isLoginMode) {
      this.authService
        .login(authForm.value)
        .subscribe({
          next: value => {
            this.isLoadingMode = false;
            console.log("response: ");
            console.log(value);
            const token = value.headers.get('authorization');
            if (token !== null) {
              console.log("token: ");
              console.log(token);
              this.authService.secureToken = token;
            }
          },
          error: err => {
            this.isLoadingMode = false;
            this.errMsg = 'Authentication information is not correct!'
            this.onShowError(this.errMsg);
          }
        });
    }
    authForm.reset();
  }

  onHandleError() {
    this.errMsg = '';
  }

  onShowError(msg: string) {
    const errorAlertComponentRef = this.viewContainerRef.createComponent(ErrorAlertComponent);
    errorAlertComponentRef.instance.errorMessage = msg;
    this.subscription = errorAlertComponentRef.instance.close.subscribe(() => {
      this.viewContainerRef.clear();
      this.subscription.unsubscribe();
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }


  isLoginMode: boolean;
  isLoadingMode: boolean;

  ngOnInit(): void {
  }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }
}
