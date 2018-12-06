import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IParams } from 'app/shared/model/params.model';
import { ParamsService } from './params.service';
import { ISample } from 'app/shared/model/sample.model';
import { SampleService } from 'app/entities/sample';

@Component({
    selector: 'jhi-params-update',
    templateUrl: './params-update.component.html'
})
export class ParamsUpdateComponent implements OnInit {
    private _params: IParams;
    isSaving: boolean;

    samples: ISample[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private paramsService: ParamsService,
        private sampleService: SampleService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ params }) => {
            this.params = params;
        });
        this.sampleService.query().subscribe(
            (res: HttpResponse<ISample[]>) => {
                this.samples = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.params.id !== undefined) {
            this.subscribeToSaveResponse(this.paramsService.update(this.params));
        } else {
            this.subscribeToSaveResponse(this.paramsService.create(this.params));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IParams>>) {
        result.subscribe((res: HttpResponse<IParams>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSampleById(index: number, item: ISample) {
        return item.id;
    }
    get params() {
        return this._params;
    }

    set params(params: IParams) {
        this._params = params;
    }
}
