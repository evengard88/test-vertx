import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import {LoginService} from "./login-service.service";
import {HttpModule} from "@angular/http";
import {RouteModuleModule} from "./route-module/route-module.module";
import {Route} from "@angular/router";
import {RouteModuleRoutingModule} from "./route-module/route-module-routing.module";
import {CanActivateViaOAuthGuardService} from "./can-activate-via-oauth-guard.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    RouteModuleRoutingModule,
    RouteModuleModule,
    HttpModule
  ],
  providers: [LoginService, RouteModuleModule, RouteModuleRoutingModule,CanActivateViaOAuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
