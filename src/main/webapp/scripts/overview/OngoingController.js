'use strict';

angular.module('fmuClientApp')
    .controller('OngoingController', ['$scope', '$filter', 'OnGoingService', 'TableService', 'ngTableParams', 'DatePickerService', 'AuthService',
        function($scope, $filter, OnGoingService, TableService, ngTableParams, DatePickerService, AuthService) {
            $scope.authService = AuthService;
            $scope.dateService = DatePickerService;
            $scope.dateService.setScope($scope);

            $scope.tableService = TableService;
            $scope.tableService.setScope($scope);

            $scope.dateKey = 'startDate';


            $scope.headerGroups = [{
                name: null,
                colorClass: null,
                children: [{
                    key: 'arendeId',
                    value: 'Ärende ID',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }, {
                    key: 'utredningType',
                    value: 'Typ',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }]
            }, {
                name: 'beställare',
                colorClass: 'bg-head-danger',
                children: [{
                    key: 'bestallareOrganisation',
                    value: 'Organisation',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }, {
                    key: 'enhet',
                    value: 'Enhet/Avdelning',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }]
            }, {
                name: 'leverantör',
                colorClass: 'bg-head-warning',
                children: [{
                    key: 'mottagarenOrganisation',
                    value: 'Organisation',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }, {
                    key: 'utredare',
                    value: 'namn',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }]
            }, {
                name: null,
                colorClass: null,
                children: [{
                    key: 'status',
                    value: 'Status',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }, {
                    key: 'startDate',
                    value: 'Utredning start',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }, {
                    key: 'daysPassed',
                    value: 'Antal dagar från start',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }, {
                    key: 'avikelser',
                    value: 'Avikelser',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }]
            }];

            $scope.footerHints = [{
                description: 'Antal dagar har överträtts och/eller annan avvikelse finns',
                colorClass: 'bg-danger'
            }];


            OnGoingService.getEavrops().then(function(result) {
                $scope.tableData = result;

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange();
                $scope.tableService.initTableParameters();
            });
        }
    ]);
