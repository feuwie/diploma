import { WishlistComponent } from './wishlist/wishlist.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { LoginComponent } from './auth/login/login.component';
import { CategoryComponent } from './category/category.component';
// import { SearchComponent } from './search/search.component';
// import { ProfileComponent } from './profile/profile.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { MainComponent } from './main/main.component';
// import { OrderComponent } from './order/order.component';
// import { CatalogComponent } from './catalog/catalog.component';
// import { ProductComponent } from './product/product.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { CatalogComponent } from './catalog/catalog.component';
import { SearchComponent } from './search/search.component';
import { ProductComponent } from './product/product.component';
import { CartComponent } from './cart/cart.component';
// import { CartComponent } from './cart/cart.component';
// import { AuthguardGuard } from 'authguard.guard';

const routes: Routes = [
    {
        path: '', component: MainComponent, pathMatch: 'full',
    },
    {
        path: 'login', component: LoginComponent,
    },
    {
        path: 'registration', component: RegistrationComponent
    },
    {
        path: 'category', component: CategoryComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: 'category/:id', component: CatalogComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: 'profile', component: ProfileComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: 'product/:article', component: ProductComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: 'cart', component: CartComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: 'search', component: SearchComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: 'wishlist', component: WishlistComponent,
        // canActivate: [AuthguardGuard]
    },
    // {
    //     path: 'order', component: OrderComponent,
    //     // canActivate: [AuthguardGuard]
    // },
    {
        path: '**', component: PagenotfoundComponent,
        // canActivate: [AuthguardGuard]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { useHash: false })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
