import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
    userName: string;

    constructor(private api: ApiService) {
    }

    ngOnInit() {
        if (sessionStorage.getItem('boo') === null) {
            sessionStorage.setItem('boo', '1');
            this.api.loginAnon().subscribe(res => {
                if (sessionStorage.getItem('auth_token') == null) {
                    this.api.storeToken(res.auth_TOKEN, 'guest');
                    sessionStorage.setItem('uuid', res.object);
                }
            });
        }
    }

    all() {
        console.log(sessionStorage.getItem('auth_token'));
    }
    // ngOnInit() {
    //     if (localStorage.getItem('boo') === null) {
    //         localStorage.setItem('boo', '1');
    //         // if (sessionStorage.getItem('boo') === '1') {
    //         //     sessionStorage.setItem('boo', '0');
    //         this.api.loginAnon().subscribe(res => {
    //             if (localStorage.getItem('auth_token') == null) {
    //                 // this.api.storeToken(res.auth_TOKEN, 'guest');
    //                 localStorage.setItem(res.auth_TOKEN, 'guest');
    //             }
    //         });
    //         // }
    //     }
    // }

    // all() {
    //     console.log(localStorage.getItem('auth_token'));
    // }
}
