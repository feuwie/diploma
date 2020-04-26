import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';
import { FormBuilder } from '@angular/forms';
import { formatDate } from '@angular/common';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    isOrder: any = false;
    isInfo: any = true;

    auth: string;
    receivedUser: any;

    bio: any;


    phones: boolean;

    // change: any = {
    //     fullname: null,
    //     dob: null,
    //     gender: this.receivedUser.gender
    // };


    timeLeft: number = 59;
    interval;
    vis: any = false;

    tester: any;
    lover: any;

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
    change = {};


    arr: any;
    groups: any;

    countere: any = 0;
    elover = [];

    private changePhone: any;

    constructor(private api: ApiService, private router: Router, private formBuilder: FormBuilder) { }

    ngOnInit() {
        this.auth = this.api.getToken();
        this.getProfile();
        this.getOrders();
        this.createForm();
    }

    getOrders() {
        this.api.getOrders(this.auth).subscribe(res => {
            console.log(res.oblist);
            this.arr = res.oblist;
            // this.receivedUser = res.object;
            // this.change = {
            //     fullname: this.receivedUser.fullname,
            //     dob: formatDate(this.receivedUser.dob, 'yyyy-MM-dd', 'en_US'),
            //     gender: this.receivedUser.gender
            // };
        }, err => {
            console.log(err);
        });
    }

    ord() {
        const groupVal = this.arr.reduce(function (obj, item) {
            obj[item.orderId] = obj[item.orderId] || [];
            obj[item.orderId].push(item);
            return obj;
        }, {});

        this.groups = Object.keys(groupVal).map(function (key) {
            return { orderId: key, item: groupVal[key] };
        });
    }

    getProfile() {
        this.api.getProfile(this.auth).subscribe(res => {
            this.receivedUser = res.object;
            this.change = {
                fullname: this.receivedUser.fullname,
                dob: formatDate(this.receivedUser.dob, 'yyyy-MM-dd', 'en_US'),
                gender: this.receivedUser.gender
            };
        }, err => {
            console.log(err);
        });
    }

    createForm() {
        this.changePhone = this.formBuilder.group({
            phone: '',
            usertype: 'customer'
        });
    }

    onOtpChange(otp) {
        this.otp = otp;
        if (otp.length == 4) {
            this.send();
        }
    }

    send() {
        this.api.phoneCheck(this.lover, this.otp, this.changePhone.value.phone).subscribe(res => {
            if (res.object == 'approved') {
                this.api.updPhone(this.auth, this.changePhone.value).subscribe(auth => {
                    this.tester = false;
                    sessionStorage.clear();
                    this.api.storeToken(auth.auth_TOKEN, "customer");
                    this.router.navigate(['']);
                    this.getProfile();
                });
            } else {
                console.log('Details cannot be empty');
            }
        });
    }

    changePhones() {
        this.timer();
        this.phones = false;
        if (this.changePhone.value.phone != this.receivedUser.phone) {
            this.tester = true;
            this.api.phoneReg(this.changePhone.value).subscribe(auth => {
                if (auth.status == '200') {
                    this.lover = auth.object;
                }
            });
        } else {
            this.changePhone.reset();
            console.log('error');
        }
    }

    changer() {
        this.tester = false;
        this.phones = true;
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

    add() {
        this.api.updProfilePersonal(this.auth, this.change).subscribe(res => {
            this.router.navigate(['']);
        }, err => {
            console.log(err);
        });
    }


    canc() {
        this.bio = false;
        this.getProfile();
    }

    delete() {
        this.api.delProfile(this.auth).subscribe(res => {
            if (res.status == '200') {
                sessionStorage.clear();
                this.router.navigate(['']);
            }
        }, err => {
            console.log(err);
        });
    }
}
