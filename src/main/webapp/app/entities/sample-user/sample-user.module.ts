import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeappSharedModule } from 'app/shared';
import {
    SampleUserComponent,
    SampleUserDetailComponent,
    SampleUserUpdateComponent,
    SampleUserDeletePopupComponent,
    SampleUserDeleteDialogComponent,
    sampleUserRoute,
    sampleUserPopupRoute
} from './';

const ENTITY_STATES = [...sampleUserRoute, ...sampleUserPopupRoute];

@NgModule({
    imports: [WeappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SampleUserComponent,
        SampleUserDetailComponent,
        SampleUserUpdateComponent,
        SampleUserDeleteDialogComponent,
        SampleUserDeletePopupComponent
    ],
    entryComponents: [SampleUserComponent, SampleUserUpdateComponent, SampleUserDeleteDialogComponent, SampleUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeappSampleUserModule {}
