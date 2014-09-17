'use strict';


describe('Controllers Tests ', function () {

    beforeEach(module('fmuClientApp'));

    describe('EavropController', function(){
        var rootScope, scope, controller, serviceMock, filter, ngTableParams, EAVROPHEADERS, DateSelectionChangeService, data;

        beforeEach(inject(function(
            $rootScope, $controller, $q, _$filter_, _ngTableParams_, _EAVROPHEADERS_, _DateSelectionChangeService_) {
            rootScope = $rootScope;
            scope = $rootScope.$new();
            controller = $controller;
            filter = _$filter_;
            ngTableParams = _ngTableParams_;
            EAVROPHEADERS = _EAVROPHEADERS_;
            DateSelectionChangeService =_DateSelectionChangeService_;

            data = [{'arendeId':'123421','utredningType':'AFU','bestallareOrganisation':'In progress','enhet':'In progress','creationTime':1445451264483,'patientCity':'LinkÃ¶ping','mottagarenOrganisation':'In progress','utredare':'In progress','status':'In progress','antalDagarEfterForfragan':93,'color':'#105cc7'},
                {'arendeId':'753423','utredningType':'SLU','bestallareOrganisation':'In progress','enhet':'In progress','creationTime':1490811264484,'patientCity':'GÃ¶teborg','mottagarenOrganisation':'In progress','utredare':'In progress','status':'In progress','antalDagarEfterForfragan':89,'color':'#10e722'},
                {'arendeId':'44240','utredningType':'AFU','bestallareOrganisation':'In progress','enhet':'In progress','creationTime':1481310864484,'patientCity':'Stockholm','mottagarenOrganisation':'In progress','utredare':'In progress','status':'In progress','antalDagarEfterForfragan':92,'color':'#105d5c'},
                {'arendeId':'78743','utredningType':'TMU','bestallareOrganisation':'In progress','enhet':'In progress','creationTime':1492884864484,'patientCity':'oskarshamn','mottagarenOrganisation':'In progress','utredare':'In progress','status':'In progress','antalDagarEfterForfragan':95,'color':'#107934'}];

            serviceMock = {
                getEavrops: function(){
                    // mock promise
                    var deferred = $q.defer();
                    deferred.resolve(data);
                    return deferred.promise;
                }
            };
        }));

        /*
        it('should call EavropService and set result in the scope', function(){
            controller('EavropController',
                {
                    $scope: scope,
                    $filter: filter,
                    EavropService: serviceMock,
                    ngTableParams: ngTableParams,
                    EAVROPHEADERS: EAVROPHEADERS,
                    DateSelectionChangeService: DateSelectionChangeService
                });

            //causes promises to check to see if they are fulfilled
            scope.$digest();
            expect(scope.eavrops).toEqual(data);
        });
        */

    });
});

