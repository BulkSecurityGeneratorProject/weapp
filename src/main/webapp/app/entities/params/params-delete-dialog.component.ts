import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParams } from 'app/shared/model/params.model';
import { ParamsService } from './params.service';

@Component({
    selector: 'jhi-params-delete-dialog',
    templateUrl: './params-delete-dialog.component.html'
})
export class ParamsDeleteDialogComponent {
    params: IParams;

    constructor(private paramsService: ParamsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paramsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'paramsListModification',
                content: 'Deleted an params'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-params-delete-popup',
    template: ''
})
export class ParamsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ params }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ParamsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.params = params;
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
