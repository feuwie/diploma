import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
    playerName: string;
    tester: any;
    lover: any;
    resend: any;


    otp: string;

    config = {
        allowNumbersOnly: false,
        length: 4,
        isPasswordInput: false,
        disableAutoFocus: false,
        placeholder: '',
        inputStyles: {
            'width': '50px',
            'height': '50px'
        }
    };
    onOtpChange(otp) {
        this.otp = otp;
        if (otp.length == 4) {
            this.send();
        }
    }

    mail: any = false;


    timeLeft: number = 59;
    interval;
    vis: any = false;

    private registerForm: any;
    private mailForm: any;
    constructor(private apiService: ApiService,
        private router: Router,
        private formBuilder: FormBuilder) {
        this.createForm();
    }

    ngOnInit() {
    }
    createForm() {
        // this.registerForm = this.formBuilder.group({
        //     email: '',
        //     password: '',
        //     username: '',
        //     usertype: 'customer'
        // });
        this.registerForm = this.formBuilder.group({
            phone: '',
            usertype: 'customer'
        });
        this.mailForm = this.formBuilder.group({
            email: '',
            password: '',
            usertype: 'customer'
        });
    }

    typeChange(frsend?: any) {
        this.mail = !this.mail;
        if (frsend == 'frsend') {
            this.tester = !this.tester;
        }
    }
    send() {
        this.apiService.phoneCheck(this.lover, this.otp, this.registerForm.value.phone).subscribe(res => {
            if (res.object == 'approved') {
                this.apiService.userLogin(this.registerForm.value).subscribe(auth => {
                    if (auth.status == '200') {
                        this.apiService.storeToken(auth.auth_TOKEN, "customer");
                        this.router.navigate(['']);
                    } else {
                        this.apiService.adminLogin(this.registerForm.value).subscribe(rez => {
                            if (rez.status == "200") {
                                this.apiService.storeToken(rez.auth_TOKEN, "admin");
                                this.router.navigate(['']);
                            } else {
                                this.apiService.register(this.registerForm.value).subscribe(res => {
                                    this.apiService.userLogin(this.registerForm.value).subscribe(auths => {
                                        this.apiService.storeToken(auths.auth_TOKEN, this.registerForm.value.usertype);
                                        this.router.navigate(['']);
                                    });
                                });
                            }
                        });
                    }
                });
            } else {
                console.log('Details cannot be empty');
            }
        });
    }

    register(): void {
        this.timer();
        this.apiService.phoneReg(this.registerForm.value).subscribe(auth => {
            if (auth.status == '200') {
                this.tester = true;
                this.lover = auth.object;
            }
        });
    }


    logMail() {
        this.apiService.userMailLogin(this.mailForm.value).subscribe(auth => {
            if (auth.status == '200') {
                sessionStorage.clear(); // check
                this.apiService.storeToken(auth.auth_TOKEN, "customer");
                this.router.navigate(['']);
            } else {
                this.apiService.adminMailLogin(this.mailForm.value).subscribe(rez => {
                    if (rez.status == "200") {
                        this.apiService.storeToken(rez.auth_TOKEN, "admin");
                        this.router.navigate(['']);
                    } else {
                        this.router.navigate(['']);
                    }
                });
            }
        });
    }

    timer() {
        this.interval = setInterval(() => {
            if (this.timeLeft > 0) {
                this.timeLeft--;
            } else {
                this.timeLeft = 59;
                this.vis = true;
            }
        }, 1000);
    }


    change() {
        this.tester = false;
        this.playerName = '';
    }



    // register(): void {
    //     this.apiService.register(this.registerForm.value).
    //         subscribe(res => {
    //             if (res.status == "400") {
    //                 console.log("Details cannot be empty");
    //             } else {
    //                 this.apiService.userLogin(this.registerForm.value).subscribe(auth => {
    //                     this.apiService.storeToken(auth.auth_TOKEN, this.registerForm.value.usertype);
    //                     this.router.navigate(['']);
    //                 });
    //             }
    //         },
    //             err => {
    //                 alert("An error has occured, Please try again !!!");
    //             });
    // }
}
