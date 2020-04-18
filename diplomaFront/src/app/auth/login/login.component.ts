import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { FormBuilder } from '@angular/forms';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    model: any = {};
    private loginForm: any;
    error = false;
    constructor(private apiService: ApiService,
        private router: Router,
        private formBuilder: FormBuilder) {
        this.createForm();
    }

    ngOnInit() {
    }
    createForm() {
        this.loginForm = this.formBuilder.group({
            email: '',
            password: ''
        });
    }
    login(): void {
        this.apiService.userLogin(this.loginForm.value).
            subscribe(res => {
                if (res.status == "200") {
                    this.apiService.storeToken(res.auth_TOKEN, "customer");
                    this.router.navigate(['']);
                    this.error = false;
                } else if (res.status == "500") {
                    this.apiService.adminLogin(this.loginForm.value).
                        subscribe(res => {
                            if (res.status == "200") {
                                this.apiService.storeToken(res.auth_TOKEN, "admin");
                                this.router.navigate(['']);
                            } else {
                                this.router.navigate(['']);
                            }
                            this.error = false;
                        },
                            err => {
                                console.log(err);
                            });
                }
            },
                err => {
                    console.log(err);
                });
    }
}
