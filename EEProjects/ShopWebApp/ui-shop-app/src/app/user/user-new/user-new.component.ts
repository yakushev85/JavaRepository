import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService, User } from 'src/app/core';

@Component({
  selector: 'app-user-new',
  templateUrl: './user-new.component.html',
  styleUrls: ['./user-new.component.css']
})
export class UserNewComponent implements OnInit {
  currentUser: User | undefined;
  userForm : FormGroup = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
    repassword: '',
    role: ''
  });
  isSubmitting = false;

  constructor(private router: Router,
    private userService: UserService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.userService.currentUser.subscribe(
      (userData) => {
        this.currentUser = userData;
      }
    );
  }

  submitForm() {
    if (!this.currentUser?.id) {
      return;
    }

    this.isSubmitting = true;

    let newUser = (this.userForm.value as { username: string, password: string, role: string });

    this.userService.add(newUser)
    .subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigateByUrl('/users/all');
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }
}
