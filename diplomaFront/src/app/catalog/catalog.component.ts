import { ApiService } from 'src/app/services/api.service';
import { Component, OnInit, Input, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-catalog',
    templateUrl: './catalog.component.html',
    styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit {

    receivedProducts: any;
    receivedCategory: any;
    receivedWishlist: any;
    receivedCart: any;

    private auth: string;
    config: any;
    addedToWishlist: any[] = [];
    testArray: any[] = [];
    testArrayTwo: any[] = [];

    constructor(private router: Router, public route: ActivatedRoute, private api: ApiService) {
        this.config = {
            currentPage: 1,
            itemsPerPage: 2
        };
        route.queryParams.subscribe(params => this.config.currentPage = params.page ? params.page : 1);
    }

    ngOnInit() {
        this.api.getProductParent(this.route.snapshot.params.id).subscribe(res => { this.receivedProducts = res.oblist; }, error => console.log(error));
        this.api.getCategoryRoute(this.route.snapshot.params.id).subscribe(res => {
            if (res.object == null) {
                this.router.navigate(['/404']);
            } else {
                this.receivedCategory = res.object;
            }
        }, error => console.log(error));
        this.getWish();
        this.getCart();
    }

    getWish() {
        if (this.api.isAuthenticated) {
            this.auth = this.api.getToken();
            this.api.getWishlist(this.auth).subscribe(res => {
                this.receivedWishlist = res.oblist;
                for (let i = 0; i < this.receivedWishlist.length; i++) {
                    this.testArray[i] = this.receivedWishlist[i].productArticle;
                }
            }, error => console.log(error));
        }
    }

    getCart() {
        if (this.api.isAuthenticated) {
            this.auth = this.api.getToken();
            this.api.getCart(this.auth).subscribe(res => {
                this.receivedCart = res.oblist;
                for (let i = 0; i < this.receivedCart.length; i++) {
                    this.testArrayTwo[i] = this.receivedCart[i].productArticle;
                }
            }, error => console.log(error));
        }
    }

    pageChange(newPage: number) {
        this.router.navigate(['category/' + this.route.snapshot.params.id], { queryParams: { page: newPage } });
    }

    addToCart(product: any) {
        this.api.addToCart(this.auth, product).subscribe((res) => {
            this.getCart();
        });
    }


    quan(product: any) {
        let prod = this.receivedCart.find(({ productId }) => productId == product.productId);
        return prod.productQuantity;
    }
    cart(product: any) {
        let prod = this.receivedCart.find(({ productId }) => productId == product.productId);
        return prod.cartId;
    }
    article(product: any) {
        let prod = this.receivedCart.find(({ productId }) => productId == product.productId);
        return prod.productArticle;
    }

    change(num: any, product: any, index: any) {
        let id = this.cart(product);
        let qty = this.quan(product);
        let article = this.article(product);
        if (num.srcElement.value > 0 && num.srcElement.value < product.productQuantity) {
            this.api.updateCart(this.auth, id, num.srcElement.value).subscribe((res) => {
                this.getCart();
            });
        }
        if (num.srcElement.value > product.productQuantity) {
            (document.getElementById(index)['value']) = qty;
            console.log('Нет необходимого кол-ва товаров!');
        }

        if (num.srcElement.value < 1) {
            this.api.deleteCart(this.auth, id).subscribe((res) => {
                this.getCart();
                this.testArrayTwo = this.testArrayTwo.filter(item => item !== article);
            });
        }
    }

    plus(product: any) {
        let qty = this.quan(product);
        let id = this.cart(product);
        if (qty < product.productQuantity) {
            qty++;
        } else {
            console.log('Нет необходимого кол-ва товаров!');
        }
        this.api.updateCart(this.auth, id, qty).subscribe((res) => {
            this.getCart();
        });
    }

    minus(product: any) {
        let qty = this.quan(product);
        let id = this.cart(product);
        let article = this.article(product);
        if (qty > 1) {
            qty--;
            this.api.updateCart(this.auth, id, qty).subscribe((res) => {
                this.getCart();
            });
        } else {
            this.api.deleteCart(this.auth, id).subscribe((res) => {
                this.getCart();
                this.testArrayTwo = this.testArrayTwo.filter(item => item !== article);
            });
        }
    }

    addToWishlist(product: any) {
        this.api.addToWishlist(this.auth, product).subscribe((res) => {
            this.getWish();
        });
    }

    delFromWishlist(product: any) {
        let prod = this.receivedWishlist.find(({ productId }) => productId == product.productId);
        this.api.delFromWishlist(this.auth, prod.wishlistId).subscribe(res => {
            this.getWish();
            this.testArray = this.testArray.filter(item => item !== prod.productArticle);
        });
    }
}
