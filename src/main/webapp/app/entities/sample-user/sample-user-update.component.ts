import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISampleUser } from 'app/shared/model/sample-user.model';
import { SampleUserService } from './sample-user.service';

@Component({
    selector: 'jhi-sample-user-update',
    templateUrl: './sample-user-update.component.html'
})
export class SampleUserUpdateComponent implements OnInit {
    private _sampleUser: ISampleUser;
    isSaving: boolean;

    constructor(private sampleUserService: SampleUserService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sampleUser }) => {
            this.sampleUser = sampleUser;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sampleUser.id !== undefined) {
            this.subscribeToSaveResponse(this.sampleUserService.update(this.sampleUser));
        } else {
            this.subscribeToSaveResponse(this.sampleUserService.create(this.sampleUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISampleUser>>) {
        result.subscribe((res: HttpResponse<ISampleUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get sampleUser() {
        return this._sampleUser;
    }

    set sampleUser(sampleUser: ISampleUser) {
        this._sampleUser = sampleUser;
    }
}
