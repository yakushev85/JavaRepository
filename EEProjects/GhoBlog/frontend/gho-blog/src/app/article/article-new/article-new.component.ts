import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/core/models/user.model';
import { ArticleService } from 'src/app/core/services/article.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-article-new',
  templateUrl: './article-new.component.html',
  styleUrls: ['./article-new.component.css']
})
export class ArticleNewComponent implements OnInit {
  isAuthenticated = false;
  isSubmitting = false;
  currentUser: User | undefined;
  

  articleForm: FormGroup  = this.fb.group({
    title: ['', Validators.required],
    content: ['', Validators.required]
  });

  constructor(
    private articleService: ArticleService, 
    private userService: UserService,
    private router: Router,
    private fb: FormBuilder
    ) {

      this.userService.isAuthenticated.subscribe(
        (value) => {
          this.isAuthenticated = value;
        }
      );

      this.userService.currentUser.subscribe(
        (value) => {
          this.currentUser = value;
        }
      )
  }

  ngOnInit(): void {
  }

  submitForm() {
    let newArticle = {
      title: this.articleForm.value.title,
      content: this.articleForm.value.content,
      authorEmail: this.currentUser?.email
    }

    this.articleService.add(newArticle).subscribe(
      () => {
        this.router.navigateByUrl('');
      }
    );
  }
}
