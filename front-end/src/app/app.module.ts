import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import { AuthComponent } from './auth/auth.component';
import {AppRoutingModule} from "./app-routing/app-routing.module";
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { PrivateZoneComponent } from './private-zone/private-zone.component';
import { PublicZoneComponent } from './public-zone/public-zone.component';
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    UnauthorizedComponent,
    NavbarComponent,
    HomeComponent,
    PrivateZoneComponent,
    PublicZoneComponent
  ],
  imports: [
    BrowserModule, FormsModule, AppRoutingModule, HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
