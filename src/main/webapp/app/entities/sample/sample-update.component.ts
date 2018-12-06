import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISample } from 'app/shared/model/sample.model';
import { SampleService } from './sample.service';
import { ISampleUser } from 'app/shared/model/sample-user.model';
import { SampleUserService } from 'app/entities/sample-user';
import { IPoint } from 'app/shared/model/point.model';
import { PointService } from 'app/entities/point';

@Component({
    selector: 'jhi-sample-update',
    templateUrl: './sample-update.component.html'
})
export class SampleUpdateComponent implements OnInit {
    private _sample: ISample;
    isSaving: boolean;

    sampleusers: ISampleUser[];

    points: IPoint[];
    sampleDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private sampleService: SampleService,
        private sampleUserService: SampleUserService,
        private pointService: PointService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sample }) => {
            this.sample = sample;
        });
        this.sampleUserService.query().subscribe(
            (res: HttpResponse<ISampleUser[]>) => {
                this.sampleusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.pointService.query().subscribe(
            (res: HttpResponse<IPoint[]>) => {
                this.points = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sample.id !== undefined) {
            this.subscribeToSaveResponse(this.sampleService.update(this.sample));
        } else {
            this.subscribeToSaveResponse(this.sampleService.create(this.sample));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISample>>) {
        result.subscribe((res: HttpResponse<ISample>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSampleUserById(index: number, item: ISampleUser) {
        return item.id;
    }

    trackPointById(index: number, item: IPoint) {
        return item.id;
    }
    get sample() {
        return this._sample;
    }

    set sample(sample: ISample) {
        this._sample = sample;
    }
}
