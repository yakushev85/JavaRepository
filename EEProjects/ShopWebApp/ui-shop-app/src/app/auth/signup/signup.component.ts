import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { UserService } from 'src/app/core';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  error: string;
  isSubmitting: boolean;
  signupForm: FormGroup;

  constructor(
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder
    ) {

    this.error = "";
    this.isSubmitting = false;

    this.signupForm = this.fb.group({
      'username': ['', Validators.required],
      'password': ['', Validators.required],
      'repassword': ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.isSubmitting = true;
    this.error = "";

    this.userService.signup(this.signupForm.value)
    .subscribe({
      next: (data) => {
        this.isSubmitting = false;
        this.router.navigateByUrl('/products/all');
      },
      error: (err) => {
        this.error = "Incorrect username or password.";
        this.isSubmitting = false;
      }
    });
  }

}
