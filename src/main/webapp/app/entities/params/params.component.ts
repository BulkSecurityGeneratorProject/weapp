import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IParams } from 'app/shared/model/params.model';
import { Principal } from 'app/core';
import { ParamsService } from './params.service';

@Component({
    selector: 'jhi-params',
    templateUrl: './params.component.html'
})
export class ParamsComponent implements OnInit, OnDestroy {
    params: IParams[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paramsService: ParamsService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.paramsService.query().subscribe(
            (res: HttpResponse<IParams[]>) => {
                this.params = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInParams();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IParams) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInParams() {
        this.eventSubscriber = this.eventManager.subscribe('paramsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
