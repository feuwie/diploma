import { Product } from './../model/product';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

    isAbout = true;
    isSpec = false;
    receivedProduct: Product;
    receivedCart: any;
    receivedWishlist: any;
    testArray: any[] = [];
    testArrayTwo: any[] = [];
    receivedCategory: any;
    private auth: string;

    constructor(private api: ApiService, private route: ActivatedRoute, private router: Router) { }

    ngOnInit() {
        this.api.getProductArticle(this.route.params['_value'].article).subscribe(res => {
            if (res.object == null) {
                this.router.navigate(['/404']);
            } else {
                this.receivedProduct = res.object;
                this.api.getCategoryRoute(this.receivedProduct.parentId).subscribe(rez => {
                    this.receivedCategory = rez.object;
                }, error => console.log(error));
            }
        }, error => console.log(error));
        this.getWish();
        this.getCart();
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

    change(num: any, product: any) {
        let id = this.cart(product);
        let qty = this.quan(product);
        let article = this.article(product);
        if (num.srcElement.value > 0 && num.srcElement.value < product.productQuantity) {
            this.api.updateCart(this.auth, id, num.srcElement.value).subscribe((res) => {
                this.getCart();
            });
        }
        if (num.srcElement.value > product.productQuantity) {
            (document.getElementById('qty')['value']) = qty;
            console.log('Неверное значение!');
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

    addToCart(product: any) {
        this.api.addToCart(this.auth, product).subscribe((res) => {
            this.getCart();
        });
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
