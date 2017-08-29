import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "../login-service.service";

@Component({
  selector: 'my-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private loginService:LoginService) {

  }

  ngOnInit() {
  }
  logout(){
    console.log("ss")
    this.loginService.logout();
    this.router.navigateByUrl('/login');
  }
}
