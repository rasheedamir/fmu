'use strict';

angular.module('fmuClientApp')
    .controller('CompletedController',
    ['$scope', '$filter', 'OverviewCompletedService', 'TableService', 'ngTableParams', 'DatePickerService',
        function ($scope, $filter, OverviewCompletedService, TableService, ngTableParams, DatePickerService) {
            $scope.dateService = DatePickerService;
            $scope.dateService.setScope($scope);
            
            $scope.tableService = TableService;
            $scope.tableService.setScope($scope);

            $scope.dateKey = 'approvedDate';

            $scope.headerGroups = [
//                {name: null, colSpan: 2, colorClass: null},
//                {name: 'beställare', colSpan: 4, colorClass: 'bg-danger'},
//                {name: 'leverantör', colSpan: 2, colorClass: 'bg-warning'},
//                {name: null, colSpan: 2, colorClass: null}
            ];
            $scope.headerNameMappings = [
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

            $scope.footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'},
                {description: 'Utredning accepterad', colorClass: 'bg-warning'},
                {description: 'Godkänd för ersättning', colorClass: 'bg-success'}
            ];

            OverviewCompletedService.getEavrops().then(function (result) {
                $scope.tableData = result;

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange();
                $scope.tableService.initTableParameters();
            });
        }]);