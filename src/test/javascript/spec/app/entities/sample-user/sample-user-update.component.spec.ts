/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WeappTestModule } from '../../../test.module';
import { SampleUserUpdateComponent } from 'app/entities/sample-user/sample-user-update.component';
import { SampleUserService } from 'app/entities/sample-user/sample-user.service';
import { SampleUser } from 'app/shared/model/sample-user.model';

describe('Component Tests', () => {
    describe('SampleUser Management Update Component', () => {
        let comp: SampleUserUpdateComponent;
        let fixture: ComponentFixture<SampleUserUpdateComponent>;
        let service: SampleUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [SampleUserUpdateComponent]
            })
                .overrideTemplate(SampleUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SampleUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleUserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SampleUser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sampleUser = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SampleUser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sampleUser = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
