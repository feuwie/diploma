import { ApiService } from 'src/app/services/api.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
    receivedProducts: any;

    constructor(private route: ActivatedRoute, private api: ApiService) {
    }

    ngOnInit() {
        this.api.getProductTitle(this.route.snapshot.queryParams.text).subscribe(res => { this.receivedProducts = res.oblist; }, error => console.log(error));
    }
}
