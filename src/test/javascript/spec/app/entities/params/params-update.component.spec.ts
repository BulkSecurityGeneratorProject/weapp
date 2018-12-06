/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WeappTestModule } from '../../../test.module';
import { ParamsUpdateComponent } from 'app/entities/params/params-update.component';
import { ParamsService } from 'app/entities/params/params.service';
import { Params } from 'app/shared/model/params.model';

describe('Component Tests', () => {
    describe('Params Management Update Component', () => {
        let comp: ParamsUpdateComponent;
        let fixture: ComponentFixture<ParamsUpdateComponent>;
        let service: ParamsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [ParamsUpdateComponent]
            })
                .overrideTemplate(ParamsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParamsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParamsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Params(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.params = entity;
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
                    const entity = new Params();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.params = entity;
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
