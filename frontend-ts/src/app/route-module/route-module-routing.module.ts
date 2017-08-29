import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "../home/home.component";
import {CanActivateViaOAuthGuardService} from "../can-activate-via-oauth-guard.service";
import {LoginComponent} from "../login/login.component";

const routes: Routes = [

  { path: 'home', component: HomeComponent , canActivate : [CanActivateViaOAuthGuardService] },
  { path: 'login', component: LoginComponent },
  { path: '',  redirectTo: '/home',  pathMatch: 'full'}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RouteModuleRoutingModule { }
