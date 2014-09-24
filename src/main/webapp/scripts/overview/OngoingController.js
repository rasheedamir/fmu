'use strict';

angular.module('fmuClientApp')
    .controller('OngoingController',
    ['$scope', '$filter', 'OnGoingService', 'TableService', 'ngTableParams', 'DatePickerService',
        function ($scope, $filter, OnGoingService, TableService, ngTableParams, DatePickerService) {
            $scope.dateService = DatePickerService;
            $scope.dateService.setScope($scope);
            
            $scope.tableService = TableService;
            $scope.tableService.setScope($scope);

            $scope.dateKey = 'startDate';
            
            $scope.headerGroups = [
                {name: null, colSpan: 2, colorClass: null},
                {name: 'beställare', colSpan: 2, colorClass: 'bg-head-danger'},
                {name: 'leverantör', colSpan: 1, colorClass: 'bg-head-warning'}
            ];

            $scope.headerNameMappings = [
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

            $scope.footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'}
            ];

            OnGoingService.getEavrops().then(function (result) {
                $scope.tableData = result;

                // Setup Datetime and Table services
                $scope.dateService.calculateInitialDateRange();
                $scope.tableService.initTableParameters();
            });
        }]);