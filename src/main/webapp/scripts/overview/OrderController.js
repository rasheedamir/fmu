'use strict';

/* Controllers */
angular.module('fmuClientApp')
    .controller('OrderController', ['$scope', '$filter', 'OrderService', 'TableService', 'ngTableParams', 'DatePickerService', 'AuthService',
        function($scope, $filter, OrderService, TableService, ngTableParams, DatePickerService, AuthService) {
            $scope.authService = AuthService;
            $scope.dateService = DatePickerService;
            $scope.dateService.setScope($scope);

            $scope.tableService = TableService;
            $scope.tableService.setScope($scope);

            $scope.dateKey = 'creationTime';

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
                }, {
                    key: 'creationTime',
                    value: 'Förfrågan skickad datum',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                }, {
                    key: 'patientCity',
                    value: 'Patientens bostadsort',
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

            $scope.getFields = function() {
                var result = [];
                _.each($scope.headerGroups, function(group) {
                    _.each(group.children, function(child){
                        if(_.intersection(child.restricted, $scope.authService.userInfo.roles).length > 0)
                            result.push(child);
                    });
                });

                return result;
            };

            $scope.restrictedFields = function(fields) {
                var total = 0;
                _.each(fields, function(field) {
                    total = _.intersection(field.restricted, $scope.authService.userInfo.roles).length > 0 ? total: total + 1;
                });

                return total;
            };

            OrderService.getEavrops().then(function(result) {
                $scope.tableData = result;

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange();
                $scope.tableService.initTableParameters();
            });
        }
    ]);
