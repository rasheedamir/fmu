'use strict';

/* Controllers */
angular.module('fmuClientApp')
    .controller('OrderController',
    ['$scope', '$filter', 'OrderService', 'TableService', 'ngTableParams', 'DatePickerService',
        function ($scope, $filter, OrderService, TableService, ngTableParams, DatePickerService) {
            $scope.dateService = DatePickerService;
            if(TableService){
                TableService.clearData();
            }
            $scope.tableService = TableService;

            $scope.dateKey = 'creationTime';
            var headerGroups = [
                {name: null, colSpan: 2, colorClass: null},
                {name: 'beställare', colSpan: 4, colorClass: 'bg-head-danger'},
                {name: 'leverantör', colSpan: 2, colorClass: 'bg-head-warning'},
                {name: null, colSpan: 2, colorClass: null}
            ];
            var headerNameMappings = [
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

            var footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'},
                {description: 'Utredning accepterad', colorClass: 'bg-warning'},
                {description: 'Godkänd för ersättning', colorClass: 'bg-success'}
            ];

            OrderService.getEavrops().then(function (result) {
                $scope.tableService.setUnfilteredData(result);

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange($scope.tableService.unfilteredData, $scope.dateKey);
                $scope.tableService.applyDateFilter($scope.dateKey, $scope.dateService.startDate, $scope.dateService.endDate);
                $scope.tableService.setHeaderGroups(headerGroups);
                $scope.tableService.setHeadersNameMapping(headerNameMappings);
                $scope.tableService.initTableParameters();
                $scope.tableService.setFooterHintCircles(footerHints);
                $scope.tableService.tableParams.settings().$scope = $scope;
                $scope.tableService.tableParams.reload();
            });
        }]);