import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Sample } from 'app/shared/model/sample.model';
import { SampleService } from './sample.service';
import { SampleComponent } from './sample.component';
import { SampleDetailComponent } from './sample-detail.component';
import { SampleUpdateComponent } from './sample-update.component';
import { SampleDeletePopupComponent } from './sample-delete-dialog.component';
import { ISample } from 'app/shared/model/sample.model';

@Injectable({ providedIn: 'root' })
export class SampleResolve implements Resolve<ISample> {
    constructor(private service: SampleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sample: HttpResponse<Sample>) => sample.body));
        }
        return of(new Sample());
    }
}

export const sampleRoute: Routes = [
    {
        path: 'sample',
        component: SampleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sample/:id/view',
        component: SampleDetailComponent,
        resolve: {
            sample: SampleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sample/new',
        component: SampleUpdateComponent,
        resolve: {
            sample: SampleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sample/:id/edit',
        component: SampleUpdateComponent,
        resolve: {
            sample: SampleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const samplePopupRoute: Routes = [
    {
        path: 'sample/:id/delete',
        component: SampleDeletePopupComponent,
        resolve: {
            sample: SampleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weappApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
