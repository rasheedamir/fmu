'use strict';

angular.module('fmuClientApp')
    .controller('CompletedController', ['$scope', '$filter', 'OverviewCompletedService', 'TableService', 'ngTableParams', 'DatePickerService', 'AuthService',
        function($scope, $filter, OverviewCompletedService, TableService, ngTableParams, DatePickerService, AuthService) {
            $scope.authService = AuthService;
            $scope.dateService = DatePickerService;
            $scope.dateService.setScope($scope);

            $scope.tableService = TableService;
            $scope.tableService.setScope($scope);

            $scope.dateKey = 'dateDelivered';

            $scope.headerGroups = [{
                name: null,
                colorClass: null,
                children: [{
                        key: 'arendeId',
                        value: 'Ärende-ID',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'utredningType',
                        value: 'Typ',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'totalDaysPassed',
                        value: 'Antal dgr klar',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'totalCompletionDays',
                        value: 'Antal dgr för komplettering',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'avikelser',
                        value: 'avikelser',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    },

                    {
                        key: 'UtredareOrganisation',
                        value: 'Utförare, organisation',
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    }, {
                        key: 'utredareAnsvarig',
                        value: 'Utförare, ansvarig',
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    }, {
                        key: 'dateDelivered',
                        value: 'Intyg levererades, datum',
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    },{
                        key: 'isCompleted',
                        value: 'Utredning komplett ?',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'approvedDate',
                        value: 'Godkänd för ersättning',
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }
                ]
            }];

            $scope.footerHints = [{
                description: 'Ej godkänd',
                colorClass: 'bg-danger'
            }, {
                description: 'avikelser finns',
                colorClass: 'bg-warning'
            }, {
                description: 'Utredning är avslutad och godkänd',
                colorClass: 'bg-success'
            }];

            $scope.dateDescription = 'Datumen utgår från det datum då intyg levererats';

            $scope.getDataValue = function(key, eavrop){
                switch(key){
                    case $scope.dateKey:
                        return $scope.dateService.getFormattedDate(eavrop[key]);
                    case 'approvedDate':
                        return $scope.dateService.getFormattedDate(eavrop[key]);
                    default:
                        return eavrop[key];
                }
            };

            OverviewCompletedService.getEavrops().then(function(result) {
                $scope.tableData = result;

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange();
                $scope.tableService.initTableParameters();
            });
        }
    ]);
