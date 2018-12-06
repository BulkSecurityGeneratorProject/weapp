import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeappSharedModule } from 'app/shared';
import {
    ParamsComponent,
    ParamsDetailComponent,
    ParamsUpdateComponent,
    ParamsDeletePopupComponent,
    ParamsDeleteDialogComponent,
    paramsRoute,
    paramsPopupRoute
} from './';

const ENTITY_STATES = [...paramsRoute, ...paramsPopupRoute];

@NgModule({
    imports: [WeappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ParamsComponent, ParamsDetailComponent, ParamsUpdateComponent, ParamsDeleteDialogComponent, ParamsDeletePopupComponent],
    entryComponents: [ParamsComponent, ParamsUpdateComponent, ParamsDeleteDialogComponent, ParamsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeappParamsModule {}
