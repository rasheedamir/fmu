'use strict';


describe('Controllers Tests ', function () {

    beforeEach(module('fmuClientApp'));

    describe('OrderController', function () {
        var rootScope, authService, scope, createController, filter, tableParams, OrderServiceMock, tableService, datePickerService, eavrops, httpBackend;

        beforeEach(inject(function ($rootScope, $controller, $q, $filter, ngTableParams, TableService, DatePickerService, $httpBackend, AuthService) {
            rootScope = $rootScope;
            scope = $rootScope.$new();
            createController = $controller;
            filter = $filter;
            tableService = TableService;
            datePickerService = DatePickerService;
            httpBackend = $httpBackend;
            tableParams = ngTableParams;
            authService = AuthService;

            eavrops = [
                {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'},
                {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1445451264483, 'patientCity': 'Farsta', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'In progress', 'status': 'Förfrågan om utredning har inkommit', 'antalDagarEfterForfragan': 3, 'color': null},
                {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm City', 'creationTime': 1490811264484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Danderyds sjukhus', 'status': 'Förfrågan tilldelas, inväntar acceptans', 'antalDagarEfterForfragan': 5, 'color': 'bg-warning'},
                {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'Försäkringskassan', 'enhet': 'Stockholm Söderort', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'Stockholms Läns Landsting', 'utredare': 'Utredare 1 i Skåne', 'status': 'Förfrågan ej accepterad av utredare', 'antalDagarEfterForfragan': 10, 'color': 'bg-danger'},
                {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'Arbetsförmedlingen', 'enhet': 'Malmö', 'creationTime': 1492884864484, 'patientCity': 'malmö', 'mottagarenOrganisation': 'Region Skåne', 'utredare': 'Region Skåne', 'status': 'In progress', 'antalDagarEfterForfragan': 15, 'color': 'bg-warning'}
            ];
            OrderServiceMock = {};
            OrderServiceMock.getEavrops = function () {
                // mock promise
                var deferred = $q.defer();
                deferred.resolve(eavrops);
                return deferred.promise;
            };

            createController = function () {
                $controller('OrderController',
                    {
                        $scope: scope,
                        $filter: filter,
                        OrderService: OrderServiceMock,
                        TableService: tableService,
                        ngTableParams: tableParams,
                        DatePickerService: datePickerService,
                        AuthService: AuthService
                    });

                scope.$digest();
            };

            authService.addRole('ROLE_SAMORDNARE');
            authService.addRole('ROLE_UTREDARE');
        }));

        it('should call OrderService getEavrops', function () {
            createController();
            expect(scope.tableData).toBe(eavrops);
        });

        it('should return the right number of row', function () {
            createController();
            expect(scope.tableData.length).toEqual(eavrops.length);
        });

        it('should return the same data contents', function () {
            createController();
            angular.forEach(scope.tableData, function (index, row) {
                angular.forEach(row, function (key, data) {
                    expect(data).toEqual(eavrops[index][key]);
                });
            });
        });

        it('should have a table service defined', function () {
            createController();
            expect(scope.tableService).toBeDefined();
        });

        it('should initialize table parameters with page count of 10 as default', function () {
            createController();
            expect(scope.tableParams.count()).toEqual(10);
        });

        it('should have a datePickerService defined', function () {
            createController();
            expect(scope.dateService).toBeDefined();
        });

        it('should return 0 number of restricted fields', function(){
            createController();
            expect(scope.restrictedFields([{restricted: []}])).toEqual(1);
            expect(scope.restrictedFields([{restricted: ['ROLE_UNDEFINED']}])).toEqual(1);
            expect(scope.restrictedFields([{restricted: ['ROLE_SAMORDNARE']}])).toEqual(0);
            expect(scope.restrictedFields([{restricted: ['ROLE_UTREDARE']}])).toEqual(0);
            expect(scope.restrictedFields([{restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']}])).toEqual(0);
        });

        it('should get correct table header fields for each role', function(){
            createController();
            expect(scope.getFields().length).toEqual(9);
            expect(_.findWhere(scope.getFields(), {key: 'patientCity'})).toBeDefined();
            authService.removeRole('ROLE_SAMORDNARE');
            expect(scope.getFields().length).toEqual(8);
            expect(_.findWhere(scope.getFields(), {key: 'patientCity'})).toBeUndefined();
        });
    });
});

