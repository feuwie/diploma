import { ApiService } from 'src/app/services/api.service';
import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';

@Component({
    selector: 'app-wishlist',
    templateUrl: './wishlist.component.html',
    styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {


    private auth: string;
    receivedWishlist: any = [];
    testArrayTwo: any[] = [];
    receivedCart: any;


    constructor(private api: ApiService) { }

    ngOnInit() {
        if (this.api.isAuthenticated) {
            this.auth = this.api.getToken();
            this.api.getWishlist(this.auth).subscribe(res => { this.receivedWishlist = res.oblist; }, error => console.log(error));
        }
        this.getCart();
    }

    delFromWishlist(index: any) {
        this.api.delFromWishlist(this.auth, this.receivedWishlist[index].wishlistId).subscribe(res => {
            this.receivedWishlist = res.oblist;
        });
    }

    sortBy(val: string): void {
        if (val === 'Сначала (а-я)') {
            this.receivedWishlist.sort((a: Product, b: Product) => {
                return a.productTitle[0].localeCompare(b.productTitle[0]);
            });
        }
        if (val === 'Сначала (я-а)') {
            this.receivedWishlist.sort((a: Product, b: Product) => {
                return b.productTitle[0].localeCompare(a.productTitle[0]);
            });
        }
        if (val === 'Сначала дешевые') {
            this.receivedWishlist.sort((a: Product, b: Product) => {
                return a.productPrice - b.productPrice;
            });
        }
        if (val === 'Сначала дорогие') {
            this.receivedWishlist.sort((a: Product, b: Product) => {
                return b.productPrice - a.productPrice;
            });
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

}
