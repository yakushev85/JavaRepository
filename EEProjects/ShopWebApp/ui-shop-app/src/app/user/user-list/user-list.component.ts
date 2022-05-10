import { Component, OnInit } from '@angular/core';
import { User, UserService } from 'src/app/core';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  currentUser: User | undefined;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAll().subscribe(
      (value) => {
        if (value) {
          this.users = value;
        }
      }
    );

    this.userService.currentUser.subscribe(
      (userData) => {
        this.currentUser = userData;
      }
    );
  }

}
