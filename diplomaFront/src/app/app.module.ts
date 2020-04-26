import { OrderComponent } from './order/order.component';
import { CartComponent } from './cart/cart.component';
import { ProductComponent } from './product/product.component';
import { SearchComponent } from './search/search.component';
import { CatalogComponent } from './catalog/catalog.component';
import { ProfileComponent } from './profile/profile.component';
import { CategoryComponent } from './category/category.component';
import { MainComponent } from './main/main.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { ApiService } from './services/api.service';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FormatPipe } from './pipe/format.pipe';
import { StorageServiceModule } from 'angular-webstorage-service';
import { WishlistComponent } from './wishlist/wishlist.component';
import { SuccessOrderComponent } from './success-order/success-order.component';
import { AuthComponent } from './auth/auth.component';
import { NgOtpInputModule } from 'ng-otp-input';


@NgModule({
    declarations: [
        AppComponent,
        FooterComponent,
        HeaderComponent,
        PagenotfoundComponent,
        MainComponent,
        CategoryComponent,
        FormatPipe,
        ProfileComponent,
        CatalogComponent,
        SearchComponent,
        ProductComponent,
        CartComponent,
        WishlistComponent,
        OrderComponent,
        SuccessOrderComponent,
        AuthComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        ReactiveFormsModule,
        FormsModule,
        AppRoutingModule,
        RouterModule,
        NgxPaginationModule,
        StorageServiceModule,
        NgOtpInputModule
    ],
    providers: [ApiService],
    bootstrap: [AppComponent]
})
export class AppModule { }
