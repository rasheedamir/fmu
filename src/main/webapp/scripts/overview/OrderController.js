'use strict';

/* Controllers */
angular.module('fmuClientApp')
    .controller('OrderController',
    ['$scope', '$filter', 'OrderService', 'TableService', 'ngTableParams', 'DatePickerService',
        function ($scope, $filter, OrderService, TableService, ngTableParams, DatePickerService) {
            $scope.dateService = DatePickerService;
            $scope.dateService.setScope($scope);
            
            $scope.tableService = TableService;
            $scope.tableService.setScope($scope);

            $scope.dateKey = 'creationTime';

            $scope.headerGroups = [
                {name: null, colSpan: 2, colorClass: null},
                {name: 'beställare', colSpan: 4, colorClass: 'bg-head-danger'},
                {name: 'leverantör', colSpan: 2, colorClass: 'bg-head-warning'},
                {name: null, colSpan: 2, colorClass: null}
            ];
            $scope.headerNameMappings = [
                {key: 'arendeId', value: 'Ärende ID'},
                {key: 'utredningType', value: 'Typ'},
                {key: 'bestallareOrganisation', value: 'Organisation'},
                {key: 'enhet', value: 'Enhet/Avdelning'},
                {key: 'creationTime', value: 'Förfrågan skickad datum'},
                {key: 'patientCity', value: 'Patientens bostadsort'},
                {key: 'mottagarenOrganisation', value: 'Organisation'},
                {key: 'status', value: 'Status'},
                {key: 'antalDagarEfterForfragan', value: 'Antal dagar efter förfrågan om utredning'}
            ];

            $scope.footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'},
                {description: 'Utredning accepterad', colorClass: 'bg-warning'},
                {description: 'Godkänd för ersättning', colorClass: 'bg-success'}
            ];

            OrderService.getEavrops().then(function (result) {
                $scope.tableData = result;

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange();
                $scope.tableService.initTableParameters();
            });
        }]);