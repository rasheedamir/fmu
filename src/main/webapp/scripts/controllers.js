'use strict';

/* Controllers */
angular.module('fmuClientApp')
    .controller('EavropsOrderController',
    ['$scope', '$filter', 'EavropService', 'TableService', 'ngTableParams', 'EAVROPHEADERS', 'DateSelectionChangeService',
        function ($scope, $filter, EavropService, TableService, ngTableParams, EAVROPHEADERS, DateSelectionChangeService) {
            $scope.dateService = DateSelectionChangeService;
            $scope.tableService = TableService;

            $scope.dateKey = 'creationTime';
            var headerGroups = [
                {name: null, colSpan: 2, colorClass: null},
                {name: 'beställare', colSpan: 4, colorClass: 'bg-danger'},
                {name: 'leverantör', colSpan: 2, colorClass: 'bg-warning'},
                {name: null, colSpan: 2, colorClass: null}
            ];
            var headerNameMappings = [
                {key: 'arendeId', value: 'Ärende ID'},
                {key: 'utredningType', value: 'Typ'},
                {key: 'enhet', value: 'Enhet/Avdelning'},
                {key: 'creationTime', value: 'Förfrågan skickad datum'},
                {key: 'patientCity', value: 'Patientens bostadsort'},
                {key: 'bestallareOrganisation', value: 'Organisation'},
                {key: 'mottagarenOrganisation', value: 'Organisation'},
                {key: 'utredare', value: 'Utredare'},
                {key: 'status', value: 'Status'},
                {key: 'antalDagarEfterForfragan', value: 'Antal dagar efter förfrågan om utredning'}
            ];

            var footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'},
                {description: 'Utredning accepterad', colorClass: 'bg-warning'},
                {description: 'Godkänd för ersättning', colorClass: 'bg-success'}
            ];

            // EavropService.getEavrops().then(function(result){
            $scope.tableService.unfilteredData =
                [
                    {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1445451264483, 'patientCity': 'Linköping', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 93, 'color': null},
                    {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1490811264484, 'patientCity': 'Göteborg', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 89, 'color': 'bg-warning'},
                    {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 92, 'color': 'bg-danger'},
                    {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1492884864484, 'patientCity': 'oskarshamn', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 95, 'color': 'bg-success'}
                ];

            // Setup Datetime and Table services
            $scope.dateService.calculateInitialDateRange($scope.tableService.unfilteredData, $scope.dateKey);
            $scope.tableService.applyDateFilter($scope.dateKey, $scope.dateService.startDate, $scope.dateService.endDate);
            $scope.tableService.setHeaderGroups(headerGroups);
            $scope.tableService.setHeadersNameMapping(headerNameMappings);
            $scope.tableService.initTableParameters();
            $scope.tableService.setFooterHintCircles(footerHints);
            $scope.tableService.tableParams.settings().$scope = $scope;
            // });
        }])
.controller('EavropsOngoingController',
    ['$scope', '$filter', 'EavropService', 'TableService', 'ngTableParams', 'EAVROPHEADERS', 'DateSelectionChangeService',
        function ($scope, $filter, EavropService, TableService, ngTableParams, EAVROPHEADERS, DateSelectionChangeService) {
            $scope.dateService = DateSelectionChangeService;
            $scope.tableService = TableService;

            $scope.dateKey = 'creationTime';
            var headerGroups = [
                {name: null, colSpan: 2, colorClass: null},
                {name: 'beställare', colSpan: 4, colorClass: 'bg-danger'},
                {name: 'leverantör', colSpan: 2, colorClass: 'bg-warning'},
                {name: null, colSpan: 2, colorClass: null}
            ];
            var headerNameMappings = [
                {key: 'arendeId', value: 'Ärende ID'},
                {key: 'utredningType', value: 'Typ'},
                {key: 'enhet', value: 'Enhet/Avdelning'},
                {key: 'creationTime', value: 'Förfrågan skickad datum'},
                {key: 'patientCity', value: 'Patientens bostadsort'},
                {key: 'bestallareOrganisation', value: 'Organisation'},
                {key: 'mottagarenOrganisation', value: 'Organisation'},
                {key: 'utredare', value: 'Utredare'},
                {key: 'status', value: 'Status'},
                {key: 'antalDagarEfterForfragan', value: 'Antal dagar efter förfrågan om utredning'}
            ];

            var footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'},
                {description: 'Utredning accepterad', colorClass: 'bg-warning'},
                {description: 'Godkänd för ersättning', colorClass: 'bg-success'}
            ];

            // EavropService.getEavrops().then(function(result){
            $scope.tableService.unfilteredData =
                [
                    {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1445451264483, 'patientCity': 'Linköping', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 93, 'color': null},
                    {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1490811264484, 'patientCity': 'Göteborg', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 89, 'color': 'bg-warning'},
                    {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 92, 'color': 'bg-danger'},
                    {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1492884864484, 'patientCity': 'oskarshamn', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 95, 'color': 'bg-success'}
                ];

            // Setup Datetime and Table services
            $scope.dateService.calculateInitialDateRange($scope.tableService.unfilteredData, $scope.dateKey);
            $scope.tableService.applyDateFilter($scope.dateKey, $scope.dateService.startDate, $scope.dateService.endDate);
            $scope.tableService.setHeaderGroups(headerGroups);
            $scope.tableService.setHeadersNameMapping(headerNameMappings);
            $scope.tableService.initTableParameters();
            $scope.tableService.setFooterHintCircles(footerHints);
            $scope.tableService.tableParams.settings().$scope = $scope;
            // });
        }])
.controller('EavropsCompletedController',
    ['$scope', '$filter', 'EavropService', 'TableService', 'ngTableParams', 'EAVROPHEADERS', 'DateSelectionChangeService',
        function ($scope, $filter, EavropService, TableService, ngTableParams, EAVROPHEADERS, DateSelectionChangeService) {
            $scope.dateService = DateSelectionChangeService;
            $scope.tableService = TableService;

            $scope.dateKey = 'creationTime';
            var headerGroups = [
                {name: null, colSpan: 2, colorClass: null},
                {name: 'beställare', colSpan: 4, colorClass: 'bg-danger'},
                {name: 'leverantör', colSpan: 2, colorClass: 'bg-warning'},
                {name: null, colSpan: 2, colorClass: null}
            ];
            var headerNameMappings = [
                {key: 'arendeId', value: 'Ärende ID'},
                {key: 'utredningType', value: 'Typ'},
                {key: 'enhet', value: 'Enhet/Avdelning'},
                {key: 'creationTime', value: 'Förfrågan skickad datum'},
                {key: 'patientCity', value: 'Patientens bostadsort'},
                {key: 'bestallareOrganisation', value: 'Organisation'},
                {key: 'mottagarenOrganisation', value: 'Organisation'},
                {key: 'utredare', value: 'Utredare'},
                {key: 'status', value: 'Status'},
                {key: 'antalDagarEfterForfragan', value: 'Antal dagar efter förfrågan om utredning'}
            ];

            var footerHints = [
                {description: 'Antal dagar har överträtts och/eller annan avvikelse finns', colorClass: 'bg-danger'},
                {description: 'Utredning accepterad', colorClass: 'bg-warning'},
                {description: 'Godkänd för ersättning', colorClass: 'bg-success'}
            ];

            // EavropService.getEavrops().then(function(result){
            $scope.tableService.unfilteredData =
                [
                    {'arendeId': '123421', 'utredningType': 'AFU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1445451264483, 'patientCity': 'Linköping', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 93, 'color': null},
                    {'arendeId': '753423', 'utredningType': 'SLU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1490811264484, 'patientCity': 'Göteborg', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 89, 'color': 'bg-warning'},
                    {'arendeId': '44240', 'utredningType': 'AFU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1481310864484, 'patientCity': 'Stockholm', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 92, 'color': 'bg-danger'},
                    {'arendeId': '78743', 'utredningType': 'TMU', 'bestallareOrganisation': 'In progress', 'enhet': 'In progress', 'creationTime': 1492884864484, 'patientCity': 'oskarshamn', 'mottagarenOrganisation': 'In progress', 'utredare': 'In progress', 'status': 'In progress', 'antalDagarEfterForfragan': 95, 'color': 'bg-success'}
                ];

            // Setup Datetime and Table services
            $scope.dateService.calculateInitialDateRange($scope.tableService.unfilteredData, $scope.dateKey);
            $scope.tableService.applyDateFilter($scope.dateKey, $scope.dateService.startDate, $scope.dateService.endDate);
            $scope.tableService.setHeaderGroups(headerGroups);
            $scope.tableService.setHeadersNameMapping(headerNameMappings);
            $scope.tableService.initTableParameters();
            $scope.tableService.setFooterHintCircles(footerHints);
            $scope.tableService.tableParams.settings().$scope = $scope;
            // });
        }]);