import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "../login-service.service";

@Component({
  selector: 'my-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginComponent]
})
export class LoginComponent {

  constructor(public router: Router ,private loginService: LoginService) {}

  login(event, username, password) {
    event.preventDefault();
    this.loginService.login(username, password)
      .subscribe(
        response => {
          localStorage.setItem('token', response.access_token);
          this.router.navigateByUrl('/home');
        },
        error => {
          alert(error);
        }
      );
  }
}

