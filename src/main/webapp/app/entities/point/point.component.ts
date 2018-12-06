import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPoint } from 'app/shared/model/point.model';
import { Principal } from 'app/core';
import { PointService } from './point.service';

@Component({
    selector: 'jhi-point',
    templateUrl: './point.component.html'
})
export class PointComponent implements OnInit, OnDestroy {
    points: IPoint[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pointService: PointService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.pointService.query().subscribe(
            (res: HttpResponse<IPoint[]>) => {
                this.points = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPoints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPoint) {
        return item.id;
    }

    registerChangeInPoints() {
        this.eventSubscriber = this.eventManager.subscribe('pointListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
