import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISampleUser } from 'app/shared/model/sample-user.model';
import { Principal } from 'app/core';
import { SampleUserService } from './sample-user.service';

@Component({
    selector: 'jhi-sample-user',
    templateUrl: './sample-user.component.html'
})
export class SampleUserComponent implements OnInit, OnDestroy {
    sampleUsers: ISampleUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sampleUserService: SampleUserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.sampleUserService.query().subscribe(
            (res: HttpResponse<ISampleUser[]>) => {
                this.sampleUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSampleUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISampleUser) {
        return item.id;
    }

    registerChangeInSampleUsers() {
        this.eventSubscriber = this.eventManager.subscribe('sampleUserListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
