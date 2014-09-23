'use strict';

angular.module('fmuClientApp')
    .controller('OngoingController',
    ['$scope', '$filter', 'OnGoingService', 'TableService', 'ngTableParams', 'DatePickerService',
        function ($scope, $filter, OnGoingService, TableService, ngTableParams, DatePickerService) {
            $scope.dateService = DatePickerService;

            // Clear the singleton
            if(TableService){
                TableService.clearData();
            }

            $scope.tableService = TableService;

            $scope.dateKey = 'startDate';
            var headerGroups = [
                {name: null, colSpan: 2, colorClass: null},
                {name: 'beställare', colSpan: 2, colorClass: 'bg-head-danger'},
                {name: 'leverantör', colSpan: 1, colorClass: 'bg-head-warning'}
            ];

            var headerNameMappings = [
                {key: 'arendeId', value: 'Ärende ID'},
                {key: 'utredningType', value: 'Typ'},
                {key: 'bestallareOrganisation', value: 'Organisation'},
                {key: 'enhet', value: 'Enhet/Avdelning'},
                {key: 'mottagarenOrganisation', value: 'Organisation'},
                {key: 'status', value: 'Status'},
                {key: 'startDate', value: 'Utredning start'},
                {key: 'daysPassed', value: 'Antal dagar från start'},
                {key: 'avikelser', value: 'Avikelser'}
            ];

            var footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'}
            ];

            OnGoingService.getEavrops().then(function (result) {
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