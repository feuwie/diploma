import { WishlistComponent } from './wishlist/wishlist.component';
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
import { OrderComponent } from './order/order.component';
import { SuccessOrderComponent } from './success-order/success-order.component';
import { AuthComponent } from './auth/auth.component';
// import { CartComponent } from './cart/cart.component';
// import { AuthguardGuard } from 'authguard.guard';

const routes: Routes = [
    {
        path: '', component: MainComponent, pathMatch: 'full',
    },
    {
        path: 'auth', component: AuthComponent,
        // canActivate: [AuthguardGuard]
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
    {
        path: 'success', component: SuccessOrderComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: 'order', component: OrderComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: '404', component: PagenotfoundComponent,
        // canActivate: [AuthguardGuard]
    },
    {
        path: '**', redirectTo: '/404',
        // canActivate: [AuthguardGuard]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { useHash: false })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
