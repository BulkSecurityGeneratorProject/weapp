import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISampleUser } from 'app/shared/model/sample-user.model';
import { SampleUserService } from './sample-user.service';

@Component({
    selector: 'jhi-sample-user-delete-dialog',
    templateUrl: './sample-user-delete-dialog.component.html'
})
export class SampleUserDeleteDialogComponent {
    sampleUser: ISampleUser;

    constructor(private sampleUserService: SampleUserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sampleUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sampleUserListModification',
                content: 'Deleted an sampleUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sample-user-delete-popup',
    template: ''
})
export class SampleUserDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sampleUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SampleUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sampleUser = sampleUser;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
