import { ApiService } from './../services/api.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  receivedCategories: any;

  constructor(private api: ApiService) { }

  ngOnInit() {
    this.api.getCategory().subscribe(res => { this.receivedCategories = res.oblist; }, error => console.log(error));
  }
}
