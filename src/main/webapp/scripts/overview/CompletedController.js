'use strict';

angular.module('fmuClientApp')
    .controller('CompletedController', ['$scope', '$filter', 'OverviewCompletedService', 'TableService', 'ngTableParams', 'DatePickerService', 'AuthService',
        function($scope, $filter, OverviewCompletedService, TableService, ngTableParams, DatePickerService, AuthService) {
            $scope.authService = AuthService;
            $scope.dateService = DatePickerService;
            $scope.dateService.setScope($scope);

            $scope.tableService = TableService;
            $scope.tableService.setScope($scope);

            $scope.dateKey = 'approvedDate';

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
                    }, {
                        key: 'totalDaysPassed',
                        value: 'Antal dagar',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'totalCompletionDays',
                        value: 'Antal dagar för komplettering',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'avikelser',
                        value: 'avikelser',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    },

                    {
                        key: 'UtredareOrganisation',
                        value: 'Vårdleverantör, organisation',
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    }, {
                        key: 'utredareAnsvarig',
                        value: 'Vårdleverantör, namn',
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    }, {
                        key: 'isCompleted',
                        value: 'Utredning komplett ?',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'approvedDate',
                        value: 'Godkänd datum',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }
                ]
            }];

            $scope.footerHints = [{
                description: 'Antal dagar har överträtts och/eller annan avvikelse finns',
                colorClass: 'bg-danger'
            }, {
                description: 'Utredning accepterad',
                colorClass: 'bg-warning'
            }, {
                description: 'Godkänd för ersättning',
                colorClass: 'bg-success'
            }];

            OverviewCompletedService.getEavrops().then(function(result) {
                $scope.tableData = result;

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange();
                $scope.tableService.initTableParameters();
            });
        }
    ]);
