'use strict';

angular.module('fmuClientApp')
    .controller('CompletedController',
    ['$scope', '$filter', 'OverviewCompletedService', 'TableService', 'ngTableParams', 'DatePickerService',
        function ($scope, $filter, OverviewCompletedService, TableService, ngTableParams, DatePickerService) {
            $scope.dateService = DatePickerService;
            if(TableService){
                TableService.clearData();
            }
            $scope.tableService = TableService;

            $scope.dateKey = 'approvedDate';
            var headerGroups = [
//                {name: null, colSpan: 2, colorClass: null},
//                {name: 'beställare', colSpan: 4, colorClass: 'bg-danger'},
//                {name: 'leverantör', colSpan: 2, colorClass: 'bg-warning'},
//                {name: null, colSpan: 2, colorClass: null}
            ];
            var headerNameMappings = [
                {key: 'arendeId', value: 'Ärende ID'},
                {key: 'utredningType', value: 'Typ'},
                {key: 'totalDaysPassed', value: 'Antal dagar'},
                {key: 'totalCompletionDays', value: 'Antal dagar för komplettering'},
                {key: 'avikelser', value: 'avikelser'},
                {key: 'UtredareOrganisation', value: 'Utredare, organisation'},
                {key: 'utredareAnsvarig', value: 'Utredare, ansvarig'},
                {key: 'isCompleted', value: 'Utredning komplett ?'},
                {key: 'approvedDate', value: 'Godkänd datum'}
            ];

            var footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'},
                {description: 'Utredning accepterad', colorClass: 'bg-warning'},
                {description: 'Godkänd för ersättning', colorClass: 'bg-success'}
            ];

            OverviewCompletedService.getEavrops().then(function (result) {
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