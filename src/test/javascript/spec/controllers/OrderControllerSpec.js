/*
'use strict';


describe('Controllers Tests ', function() {

    beforeEach(module('fmuClientApp'));

    describe('OrderController', function() {
        var rootScope, scope, createController, filter, tableParams, OrderServiceMock, tableService, datePickerService, eavrops, httpBackend;

        beforeEach(inject(function($rootScope, $controller, $q, $filter, ngTableParams, TableService, DatePickerService, $httpBackend, AuthService) {
            rootScope = $rootScope;
            scope = $rootScope.$new();
            createController = $controller;
            filter = $filter;
            tableService = TableService;
            datePickerService = DatePickerService;
            httpBackend = $httpBackend;
            tableParams = ngTableParams;

            eavrops = [];
            OrderServiceMock = {};
            OrderServiceMock.getEavrops = function() {
                // mock promise
                var deferred = $q.defer();
                deferred.resolve(eavrops);
                return deferred.promise;
            };

            createController = function() {
                $controller('OrderController', {
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
        }));

        */
/*it('should call OrderService getEavrops', function() {
            createController();
            expect(scope.tableData).toBe(eavrops);
        });

        it('should return the right number of row', function() {
            createController();
            expect(scope.tableData.length).toEqual(eavrops.length);
        });*//*


        it('should return the same data contents', function() {
            createController();
            angular.forEach(scope.tableData, function(index, row) {
                angular.forEach(row, function(key, data) {
                    expect(data).toEqual(eavrops[index][key]);
                });
            });
        });

        */
/*it('should have a table service defined', function() {
            createController();
            expect(scope.tableService).toBeDefined();
        });*//*


        */
/*it('should initialize table parameters with page count of 10 as default', function() {
            createController();
            expect(scope.tableParams.count()).toEqual(10);
        });*//*


        */
/*it('should have a datePickerService defined', function() {
            createController();
            expect(scope.dateService).toBeDefined();
        });*//*

    });
});
*/
