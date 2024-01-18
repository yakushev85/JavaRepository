import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, User } from '@auth0/auth0-angular';
import { ArticleService } from 'src/app/core/services/article.service';

@Component({
  selector: 'app-article-new',
  templateUrl: './article-new.component.html',
  styleUrls: ['./article-new.component.css']
})
export class ArticleNewComponent implements OnInit {
  isSubmitting = false;
  currentUser: User | undefined;

  articleForm: FormGroup  = this.fb.group({
    title: ['', Validators.required],
    content: ['', Validators.required]
  });

  constructor(
    private articleService: ArticleService, 
    private router: Router,
    private fb: FormBuilder,
    private auth: AuthService
    ) {

      this.auth.user$.subscribe((value) => {
        if (value) {
          this.currentUser = value;
        }
      })
  }

  ngOnInit(): void {
  }

  submitForm() {
    let newArticle = {
      title: this.articleForm.value.title,
      content: this.articleForm.value.content,
      authorEmail: this.currentUser?.email,
      authorName: this.currentUser?.nickname
    }

    this.articleService.add(newArticle).subscribe(
      () => {
        this.router.navigateByUrl('');
      }
    );
  }
}
