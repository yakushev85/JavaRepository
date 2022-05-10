import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserService } from 'src/app/core';

@Component({
  selector: 'app-user-item',
  templateUrl: './user-item.component.html',
  styleUrls: ['./user-item.component.css']
})
export class UserItemComponent implements OnInit {
  user: User | undefined;
  currentUser: User | undefined;
  userForm : FormGroup  = this.fb.group({
    password: '',
    repassword: '',
    role: ''
  });
  isSubmitting = false;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data) => {
        this.user = (data as { user: User }).user;
        this.userForm.patchValue(this.user);
      }
    );

    this.userService.currentUser.subscribe(
      (userData) => {
        this.currentUser = userData;
      }
    );
  }

  submitForm() {
    if (!this.user && this.currentUser?.id) {
      return;
    }

    this.isSubmitting = true;

    let updatedUserData = this.userForm.value as {password: string, role: string };

    // post the changes
    this.userService.update({
      id: this.user?.id,
      password: updatedUserData.password,
      role: updatedUserData.role
    })
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
