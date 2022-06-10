import { Component, OnInit } from '@angular/core';
import { Article } from 'src/app/core/models/article.model';
import { ArticleService } from 'src/app/core/services/article.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent implements OnInit {
  articles: Article[] = [];
  isAuthenticated = false;
  pageNumber = 1;
  pageSize = 10;
  totalElements = 0;


  constructor(
    private articleService: ArticleService,
    private userService: UserService) { }

  ngOnInit(): void {
    this.listValues();

    this.userService.isAuthenticated.subscribe(
      (value) => {
        this.isAuthenticated = value;
      }
    );
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
