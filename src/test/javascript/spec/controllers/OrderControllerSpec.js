'use strict';


describe('Controllers Tests ', function () {

    beforeEach(module('fmuClientApp'));

    describe('OrderController', function () {
        var rootScope, scope, createController, filter, tableParams, eavropServiceMock, tableService, datePickerService, eavrops, httpBackend;

        beforeEach(inject(function ($rootScope, $controller, $q, $filter, ngTableParams, TableService, DatePickerService, $httpBackend) {
            rootScope = $rootScope;
            scope = $rootScope.$new();
            createController = $controller;
            filter = $filter;
            tableService = TableService;
            datePickerService = DatePickerService;
            httpBackend = $httpBackend;
            tableParams = ngTableParams;

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
            eavropServiceMock = {};
            eavropServiceMock.getEavrops = function () {
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
                        EavropService: eavropServiceMock,
                        TableService: tableService,
                        ngTableParams: tableParams,
                        DatePickerService: datePickerService
                    });

                scope.$digest();
            };
        }));

        it('should call EavropService getEavrops', function () {
            createController();
            expect(scope.tableService.unfilteredData).toBe(eavrops);
        });

        it('should return the right number of row', function () {
            createController();
            expect(scope.tableService.unfilteredData.length).toEqual(eavrops.length);
        });

        it('should return the same data contents', function () {
            createController();
            angular.forEach(scope.tableService.unfilteredData, function (index, row) {
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
            expect(scope.tableService.tableParams.count()).toEqual(10);
        });

        it('should have a datePickerService defined', function () {
            createController();
            expect(scope.dateService).toBeDefined();
        });
    });
});

