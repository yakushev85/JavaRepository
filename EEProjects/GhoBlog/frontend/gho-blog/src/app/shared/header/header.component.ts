import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/models/user.model';
import { UserService } from 'src/app/core/services/user.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAuthenticated = false;
  user: User | undefined;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.isAuthenticated.subscribe(
      (value) => {
        this.isAuthenticated = value;
      }
    );

    this.userService.currentUser.subscribe(
      (value) => {
        this.user = value;
      }
    );
  }

  doLogout() {
    this.userService.logout();
  }
}
