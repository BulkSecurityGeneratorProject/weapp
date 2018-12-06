import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WeappProjectModule } from './project/project.module';
import { WeappTaskModule } from './task/task.module';
import { WeappPointModule } from './point/point.module';
import { WeappSampleUserModule } from './sample-user/sample-user.module';
import { WeappSampleModule } from './sample/sample.module';
import { WeappParamsModule } from './params/params.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        WeappProjectModule,
        WeappTaskModule,
        WeappPointModule,
        WeappSampleUserModule,
        WeappSampleModule,
        WeappParamsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeappEntityModule {}
