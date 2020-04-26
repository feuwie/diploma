import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef, Inject, AfterViewInit, OnDestroy } from '@angular/core';
import { ApiService } from '../services/api.service';
import { NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
    selector: 'app-order',
    templateUrl: './order.component.html',
    styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild('cardInfo', { static: false }) cardInfo: ElementRef;

    card: any;
    cardHandler = this.onChange.bind(this);
    error: string;



    auth: string;
    receivedUser: any;
    lover: any;

    receivedArr: any[] = [];

    constructor(private api: ApiService, private http: HttpClient, private cd: ChangeDetectorRef, private router: Router) {
    }

    ngOnInit() {
        this.lover = Number(localStorage.getItem('cartTotal')) * 100;
        // this.lover = sessionStorage.getItem('test');
        this.auth = this.api.getToken();
        this.api.getProfile(this.auth).subscribe(res => { this.receivedUser = res.object; console.log(res); }, err => {
            console.log(err);
        });
    }


    ngAfterViewInit() {
        this.card = elements.create('card');
        this.card.mount(this.cardInfo.nativeElement);
        this.card.addEventListener('change', this.cardHandler);
    }

    ngOnDestroy() {
        this.card.removeEventListener('change', this.cardHandler);
        this.card.destroy();
    }

    onChange({ error }) {
        if (error) {
            this.error = error.message;
        } else {
            this.error = null;
        }
        this.cd.detectChanges();
    }

    async onSubmit(form: NgForm) {
        const { token, error } = await stripe.createToken(this.card);

        if (error) {
            console.log('Something is wrong:', error);
        } else {
            console.log('Success!', token);
            this.api.chargeCard(token, this.lover, this.receivedUser.email).subscribe(res => {
                localStorage.clear();
                this.router.navigate(['success']);
                this.api.makeOrder(this.auth).subscribe(rez => {
                    console.log(rez);
                });
            });
        }
    }
}
