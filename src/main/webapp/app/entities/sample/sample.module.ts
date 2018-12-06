import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeappSharedModule } from 'app/shared';
import {
    SampleComponent,
    SampleDetailComponent,
    SampleUpdateComponent,
    SampleDeletePopupComponent,
    SampleDeleteDialogComponent,
    sampleRoute,
    samplePopupRoute
} from './';

const ENTITY_STATES = [...sampleRoute, ...samplePopupRoute];

@NgModule({
    imports: [WeappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SampleComponent, SampleDetailComponent, SampleUpdateComponent, SampleDeleteDialogComponent, SampleDeletePopupComponent],
    entryComponents: [SampleComponent, SampleUpdateComponent, SampleDeleteDialogComponent, SampleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeappSampleModule {}
