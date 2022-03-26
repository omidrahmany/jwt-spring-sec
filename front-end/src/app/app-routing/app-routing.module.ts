import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {AuthComponent} from "../auth/auth.component";
import {HomeComponent} from "../home/home.component";
import {PrivateZoneComponent} from "../private-zone/private-zone.component";
import {PublicZoneComponent} from "../public-zone/public-zone.component";

const appRoutes: Routes = [
  {path: "", component: HomeComponent},
  {path: "auth", component: AuthComponent},
  {path: "private-zone", component: PrivateZoneComponent},
  {path: "public-zone", component: PublicZoneComponent},


  // {path:"unauthorized",  }
]


@NgModule({
  declarations: [],
  imports: [
    CommonModule, RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
