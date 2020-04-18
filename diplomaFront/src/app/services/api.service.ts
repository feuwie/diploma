import { Injectable, Inject, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SESSION_STORAGE, StorageService } from 'angular-webstorage-service';
import { User } from '../model/user';

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    @Output() loggedIn: EventEmitter<any> = new EventEmitter<any>();
    @Output() changeAction: EventEmitter<any> = new EventEmitter<any>();

    number: any;

    constructor(@Inject(SESSION_STORAGE) private storage: StorageService, private http: HttpClient) { }

    register(user: User): Observable<any> {
        return this.http.post('http://localhost:8080/user/registration', user);
    }

    userLogin(user: User): Observable<any> {
        return this.http.post('http://localhost:8080/user/login', user);
    }

    adminLogin(user: User): Observable<any> {
        return this.http.post('http://localhost:8080/admin/login', user);
    }

    // category

    getCategoryRoute(route: any): Observable<any> {
        this.number = Number(route);
        return this.http.post<any>('http://localhost:8080/categoryroute', this.number);
    }

    getCategory(): Observable<any> {
        return this.http.get<any>('http://localhost:8080/category');
    }

    // product

    getProductParent(id: string): Observable<any> {
        this.number = Number(id);
        const body = { parentId: this.number };
        return this.http.post<any>('http://localhost:8080/{parent}', body);
    }

    getProductTitle(title: string): Observable<any> {
        const body = { productTitle: title };
        return this.http.post<any>('http://localhost:8080/{title}', body);
    }

    getProductArticle(article: string): Observable<any> {
        this.number = Number(article);
        const body = { productArticle: this.number };
        return this.http.post<any>('http://localhost:8080/{article}', body);
    }

    getProducts(): Observable<any> {
        return this.http.get<any>('http://localhost:8080/getproducts');
    }

    // profile

    getProfile(auth: string): Observable<any> {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.post<any>('http://localhost:8080/profile', null, { headers: myheader }); // change
    }


    // cart

    getCart(auth: string): Observable<any> {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.get<any>('http://localhost:8080/cart', { headers: myheader });
    }

    addToCart(auth: string, product: any): Observable<any> {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.get<any>('http://localhost:8080/addcart?productId=' + product.productId, { headers: myheader });
    }

    updateCart(auth: string, cartid: number, qty: number): Observable<any> {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.get<any>('http://localhost:8080/updcart' + '?cartid=' + cartid + '&quantity=' + qty, { headers: myheader });
    }

    deleteCart(auth: string, cartid: number): Observable<any> {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.get<any>('http://localhost:8080/delcart' + '?cartid=' + cartid, { headers: myheader });
    }

    // wishlist

    getWishlist(auth: string): Observable<any> {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.get<any>('http://localhost:8080/wishlist', { headers: myheader });
    }

    addToWishlist(auth: string, product: any): Observable<any> {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.get<any>('http://localhost:8080/addwishlist?productId=' + product.productId, { headers: myheader });
    }

    delFromWishlist(auth: string, wishlistId: number): Observable<any> {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.get<any>('http://localhost:8080/delwishlist?wishlistid=' + wishlistId, { headers: myheader });
    }


    // auth

    public isAuthenticated(): boolean {
        return this.getToken() !== null;
    }

    storeToken(token: string, auth_type: string) {
        this.loggedIn.emit(auth_type);
        this.storage.set('auth_token', token);
        this.storage.set('auth_type', auth_type);
    }

    getEmitter() {
        return this.loggedIn;
    }

    getAuthType(): string {
        if (this.storage.get('auth_type') !== null) {
            return this.storage.get('auth_type');
        }
        return null;
    }

    getToken() {
        return this.storage.get('auth_token');
    }

    // removeToken() {
    //     this.storage.remove("auth_type");
    //     return this.storage.remove("auth_token");
    // }


    // promo

    getPromo(): Observable<any> {
        return this.http.get<any>('http://localhost:8080/getpromo');
    }


    // payment

    chargeCard(token?: any, amount?: any, email?: any) {
        const body = { userEmail: email, userAmount: amount, token };
        return this.http.post<any>('http://localhost:8080/payment', body);
    }

    // order

    makeOrder(auth: any) {
        const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
        return this.http.get<any>('http://localhost:8080/makeorder', { headers: myheader });
    }
}
