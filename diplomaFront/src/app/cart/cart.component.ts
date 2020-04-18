import { ApiService } from 'src/app/services/api.service';
import { Component, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-cart',
    templateUrl: './cart.component.html',
    styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

    name: string = "Tom";
    age: number = 24;

    private auth: string;
    receivedCart: any = [];
    totalSum: any = 0;

    testArray: any[] = [];
    receivedWishlist: any;
    receivedProducts: any;
    testArrayTwo: any[] = [];

    prodQty: any;

    promo: any[] = [];

    discount = 0;

    cartPromo = 0;
    receivedPromos: any = null;
    cartQty: any;

    cartTotal = 0;

    receivedArr: any;

    constructor(private api: ApiService, private router: Router) {
    }

    setTotal() {
        localStorage.setItem('cartTotal', String(this.cartPromo));
    }

    ngOnInit() {
        this.api.getProducts().subscribe(res => { this.receivedProducts = res.oblist; }, error => console.log(error));
        this.getCart();
        this.getWish();
        this.loadPromo();
    }

    loadPromo() {
        this.api.getPromo().subscribe(res => {
            this.receivedPromos = res.oblist;
            for (let i = 0; i < this.receivedPromos.length; i++) {
                this.promo[i] = this.receivedPromos[i].promoCode;
            }
        });
    }

    calcQty() {
        this.cartQty = 0;
        this.receivedCart.forEach(res => {
            this.cartQty += Number(res.productQuantity);
        });
    }

    getCart() {
        if (this.api.isAuthenticated) {
            this.auth = this.api.getToken();
            this.api.getCart(this.auth).subscribe(res => {
                this.receivedCart = res.oblist;
                for (let i = 0; i < this.receivedCart.length; i++) {
                    this.testArrayTwo[i] = this.receivedCart[i].productArticle;
                }
                this.calcCartTotal();
                this.calcQty();
                this.loadPromo();
                this.addPromo();
            }, error => console.log(error));
        }
    }

    calcCartTotal() {
        this.cartTotal = 0;
        this.receivedCart.forEach(item => {
            this.cartTotal += (item.productQuantity * item.productPrice);
        });
        this.cartPromo = this.cartTotal;
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

    qty(product: any) {
        let prod = this.receivedProducts.find(({ productId }) => productId == product.productId);
        return prod.productQuantity;
    }

    change(num: any, product: any, index: any) {
        console.log(index);
        let id = this.cart(product);
        let qty = this.quan(product);
        let article = this.article(product);
        let prodQty = this.qty(product);
        if (num.srcElement.value > 0 && num.srcElement.value < prodQty) {
            this.api.updateCart(this.auth, id, num.srcElement.value).subscribe((res) => {
                this.getCart();
            });
        }
        if (num.srcElement.value > prodQty) {
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
        let prodQty = this.qty(product);
        if (qty < prodQty) {
            qty++;
            this.api.updateCart(this.auth, id, qty).subscribe((res) => {
                this.getCart();
            });
        } else {
            console.log('Нет необходимого кол-ва товаров!');
        }
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


    delFromCart(product: any) {
        let id = this.cart(product);
        this.api.deleteCart(this.auth, id).subscribe((res) => {
            this.getCart();
        });
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


    addPromo(title?: string) {
        if (localStorage.getItem('promo') != null) {
            title = localStorage.getItem('promo');
        }
        if (this.promo.includes(title)) {
            let prod = this.receivedPromos.find(({ promoCode }) => promoCode == title);
            if (prod.promoType == 'nominal') {
                if (prod.promoValue < this.cartTotal) {
                    localStorage.setItem('promo', title);
                    this.discount = prod.promoValue;
                    this.cartPromo = this.cartTotal - this.discount;
                } else {
                    console.log('error');
                }
            }
            if (prod.promoType == 'percentage') {
                localStorage.setItem('promo', title);
                this.discount = Math.round(this.cartTotal / 100 * prod.promoValue);
                this.cartPromo = this.cartTotal - this.discount;
            }
        }
    }

    delPromo() {
        localStorage.removeItem('promo');
        if (document.getElementById('titleInput') != null) {
            (document.getElementById('titleInput') as HTMLInputElement).value = '';
        }
        this.cartPromo = this.cartTotal;
        this.discount = 0;
    }
}
