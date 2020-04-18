import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';
import { Product } from '../model/product';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    product: Product = new Product();

    private loggedType: any;
    constructor(private auth: ApiService, private router: Router) {
        if (this.auth.getAuthType() == null) {
            this.loggedType = '';
        } else {
            if (this.auth.getAuthType() == 'customer') {
                this.loggedType = 'customer';
            } else if (this.auth.getAuthType() == 'admin') {
                this.loggedType = 'admin';
            }
        }
        this.auth.getEmitter().subscribe((type) => {
            this.loggedType = type;
        });
    }


    ngOnInit() {
    }

    logout() {
        this.loggedType = '';
        localStorage.clear();
        sessionStorage.clear();
        this.router.navigate(['']);
    }

    search() {
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() =>
            this.router.navigate(['search'], { queryParams: { text: this.product.productTitle } })).then(() => this.product.productTitle = '');
    }
}
