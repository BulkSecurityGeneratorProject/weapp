import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Params } from 'app/shared/model/params.model';
import { ParamsService } from './params.service';
import { ParamsComponent } from './params.component';
import { ParamsDetailComponent } from './params-detail.component';
import { ParamsUpdateComponent } from './params-update.component';
import { ParamsDeletePopupComponent } from './params-delete-dialog.component';
import { IParams } from 'app/shared/model/params.model';

@Injectable({ providedIn: 'root' })
export class ParamsResolve implements Resolve<IParams> {
    constructor(private service: ParamsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((params: HttpResponse<Params>) => params.body));
        }
        return of(new Params());
    }
}

export const paramsRoute: Routes = [
    {
        path: 'params',
        component: ParamsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.params.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'params/:id/view',
        component: ParamsDetailComponent,
        resolve: {
            params: ParamsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.params.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'params/new',
        component: ParamsUpdateComponent,
        resolve: {
            params: ParamsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.params.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'params/:id/edit',
        component: ParamsUpdateComponent,
        resolve: {
            params: ParamsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.params.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paramsPopupRoute: Routes = [
    {
        path: 'params/:id/delete',
        component: ParamsDeletePopupComponent,
        resolve: {
            params: ParamsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.params.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
