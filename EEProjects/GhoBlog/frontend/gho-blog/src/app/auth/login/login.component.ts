import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  error: string;
  isSubmitting: boolean;
  loginForm: FormGroup;

  constructor(
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder
    ) {

    this.error = "";
    this.isSubmitting = false;

    this.loginForm = this.fb.group({
      'email': ['', [Validators.required, Validators.minLength(6)]],
      'password': ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.isSubmitting = true;
    this.error = "";

    this.userService.login(this.loginForm.value)
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
