/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WeappTestModule } from '../../../test.module';
import { SampleUserDeleteDialogComponent } from 'app/entities/sample-user/sample-user-delete-dialog.component';
import { SampleUserService } from 'app/entities/sample-user/sample-user.service';

describe('Component Tests', () => {
    describe('SampleUser Management Delete Component', () => {
        let comp: SampleUserDeleteDialogComponent;
        let fixture: ComponentFixture<SampleUserDeleteDialogComponent>;
        let service: SampleUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [SampleUserDeleteDialogComponent]
            })
                .overrideTemplate(SampleUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SampleUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleUserService);
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
