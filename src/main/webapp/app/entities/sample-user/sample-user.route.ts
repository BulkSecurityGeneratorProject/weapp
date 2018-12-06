import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SampleUser } from 'app/shared/model/sample-user.model';
import { SampleUserService } from './sample-user.service';
import { SampleUserComponent } from './sample-user.component';
import { SampleUserDetailComponent } from './sample-user-detail.component';
import { SampleUserUpdateComponent } from './sample-user-update.component';
import { SampleUserDeletePopupComponent } from './sample-user-delete-dialog.component';
import { ISampleUser } from 'app/shared/model/sample-user.model';

@Injectable({ providedIn: 'root' })
export class SampleUserResolve implements Resolve<ISampleUser> {
    constructor(private service: SampleUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sampleUser: HttpResponse<SampleUser>) => sampleUser.body));
        }
        return of(new SampleUser());
    }
}

export const sampleUserRoute: Routes = [
    {
        path: 'sample-user',
        component: SampleUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sampleUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sample-user/:id/view',
        component: SampleUserDetailComponent,
        resolve: {
            sampleUser: SampleUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sampleUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sample-user/new',
        component: SampleUserUpdateComponent,
        resolve: {
            sampleUser: SampleUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sampleUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sample-user/:id/edit',
        component: SampleUserUpdateComponent,
        resolve: {
            sampleUser: SampleUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sampleUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sampleUserPopupRoute: Routes = [
    {
        path: 'sample-user/:id/delete',
        component: SampleUserDeletePopupComponent,
        resolve: {
            sampleUser: SampleUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sampleUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
