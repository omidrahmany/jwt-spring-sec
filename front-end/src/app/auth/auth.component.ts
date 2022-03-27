import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthService} from "../servic/auth.service";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  isLoginMode: boolean;
  isLoadingMode: boolean;

  constructor(private authService: AuthService) {
    this.isLoginMode = true;
    this.isLoadingMode = false;
  }

  ngOnInit(): void {
  }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit(authForm: NgForm) {
    this.isLoadingMode = true
    console.log(authForm.value);
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
          }
        });
    }
    authForm.reset();

  }
}
