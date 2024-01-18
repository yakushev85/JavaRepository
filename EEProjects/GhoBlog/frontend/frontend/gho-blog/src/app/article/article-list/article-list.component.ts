import { Component, OnInit } from '@angular/core';
import { AuthService, User } from '@auth0/auth0-angular';
import { Article } from 'src/app/core/models/article.model';
import { ArticleService } from 'src/app/core/services/article.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent implements OnInit {
  articles: Article[] = [];
  pageNumber = 1;
  pageSize = 10;
  totalElements = 0;


  constructor(
    private articleService: ArticleService, 
    public auth: AuthService) { }

  ngOnInit(): void {
    this.listValues();
  }

  listValues() {
    this.articleService.getAll(this.pageNumber - 1, this.pageSize).subscribe(
      (value) => {
        if (value) {
          this.articles = (value.content as Article[]);
          this.pageNumber = value.pageable.pageNumber + 1;
          this.pageSize = value.pageable.pageSize;
          this.totalElements = value.totalElements;
        }
      }
    );
  }

  updatePageSize(value: string) {
    this.pageSize = +value;
    this.listValues();
  }
}
