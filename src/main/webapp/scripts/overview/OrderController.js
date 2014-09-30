'use strict';

/* Controllers */
angular.module('fmuClientApp')
    .controller('OrderController', ['$scope','$state', '$filter', 'OrderService', 'TableService', 'ngTableParams', 'DatePickerService', 'AuthService',
        function($scope, $state, $filter, OrderService, TableService, ngTableParams, DatePickerService, AuthService) {
            $scope.authService = AuthService;
            $scope.dateService = DatePickerService;
            $scope.dateService.setScope($scope);

            $scope.tableService = TableService;
            $scope.tableService.setScope($scope);

            $scope.dateKey = 'creationTime';

            $scope.click = function(id){
                $state.go('eavrop.order', {eavropId: id});
            };

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
                }, {
                    key: 'creationTime',
                    value: 'Förfrågan skickad datum',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }, {
                    key: 'patientCity',
                    value: 'Den försäkrades bostadsort',
                    restricted: ['ROLE_SAMORDNARE']
                }]
            }, {
                name: 'leverantör',
                colorClass: 'bg-head-warning',
                children: [{
                    key: 'mottagarenOrganisation',
                    value: 'Organisation',
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
                    key: 'antalDagarEfterForfragan',
                    value: 'Antal dagar efter förfrågan om utredning',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }]
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

            $scope.dateDescription = 'Datumen utgår från det datum då beställningen inkommit';

            $scope.getDataValue = function(key, eavrop){
                switch(key){
                    case $scope.dateKey:
                        return $scope.dateService.getFormattedDate(eavrop[key]);
                    default:
                        return eavrop[key];
                }
            };

            OrderService.getEavrops().then(function(result) {
                $scope.tableData = result;

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange();
                $scope.tableService.initTableParameters();
            });
        }
    ]);
