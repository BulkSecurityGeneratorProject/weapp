/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WeappTestModule } from '../../../test.module';
import { ParamsDeleteDialogComponent } from 'app/entities/params/params-delete-dialog.component';
import { ParamsService } from 'app/entities/params/params.service';

describe('Component Tests', () => {
    describe('Params Management Delete Component', () => {
        let comp: ParamsDeleteDialogComponent;
        let fixture: ComponentFixture<ParamsDeleteDialogComponent>;
        let service: ParamsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [ParamsDeleteDialogComponent]
            })
                .overrideTemplate(ParamsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParamsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParamsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
