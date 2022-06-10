import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';

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
      'email': ['', [Validators.required, Validators.minLength(6)]],
      'username': ['', [Validators.required, Validators.minLength(3)]],
      'password': ['', [Validators.required, Validators.minLength(6)]],
      'repassword': ['', [Validators.required, Validators.minLength(6)]]
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
        this.router.navigateByUrl('');
      },
      error: (err) => {
        this.error = err.error.errorMessage;
        this.isSubmitting = false;
      }
    });
  }

}
