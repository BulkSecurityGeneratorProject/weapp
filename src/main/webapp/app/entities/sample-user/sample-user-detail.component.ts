import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISampleUser } from 'app/shared/model/sample-user.model';

@Component({
    selector: 'jhi-sample-user-detail',
    templateUrl: './sample-user-detail.component.html'
})
export class SampleUserDetailComponent implements OnInit {
    sampleUser: ISampleUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sampleUser }) => {
            this.sampleUser = sampleUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
