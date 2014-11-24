'use strict';

angular.module('fmuClientApp')
    .controller('OngoingController', ['$scope', '$filter', 'AuthService', 'EAVROP_STATUS', 'EAVROP_TABLE',
        function ($scope, $filter, AuthService, EAVROP_STATUS, EAVROP_TABLE) {
            $scope.authService = AuthService;
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.endDate.setMonth($scope.startDate.getMonth() + 1);
            $scope.startDate.setMonth($scope.startDate.getMonth() - 1);
            $scope.dateKey = 'startDate';

            $scope.ongoingStatus = EAVROP_STATUS.accepted;

            $scope.headerFields = [
                {
                    key: 'arendeId',
                    name: 'Ärende-ID'
                },
                {
                    key: 'utredningType',
                    name: 'Typ'
                },
                {
                    key: 'bestallareOrganisation',
                    name: 'Organisation'
                },
                {
                    key: 'bestallareEnhet',
                    name: 'Enhet/Avdelning'
                },
                {
                    key: 'utredareOrganisation',
                    name: 'Organisation'
                },
                {
                    key: 'status',
                    name: 'Status'
                },
                {
                    key: 'startDate',
                    name: 'Utredning start'
                },
                {
                    key: 'nrOfDaysSinceStart',
                    name: 'Antal dagar från start'
                },
                {
                    key: 'avikelser',
                    name: 'Avikelser'
                }
            ];

            $scope.headerGroups = [
                {
                    name: null,
                    colorClass: null,
                    colspan: 2
                },
                {
                    name: 'beställare',
                    colorClass: 'bg-head-danger',
                    colspan: 2
                },
                {
                    name: 'leverantör',
                    colorClass: 'bg-head-warning',
                    colspan: 1
                },
                {
                    name: null,
                    colorClass: null,
                    colspan: 4
                }
            ];

            $scope.footerHints = [
                {
                    description: 'Antal dagar har överträtts och/eller annan avvikelse finns',
                    colorClass: 'bg-danger'
                }
            ];

            $scope.datePickerDescription = 'Datumen utgår från det datum då utredningen startat';

            $scope.visa = function () {
                if($scope.tableParameters){
                    $scope.tableParameters.reload();
                }
            };

            $scope.getTableCellValue = function (key, rowData) {
                switch (key) {
                    case 'startDate':
                        return $filter('date')(rowData[key], EAVROP_TABLE.dateFormat);
                    case 'status':
                        return EAVROP_TABLE.statusMapping[rowData[key]];
                    default:
                        return rowData[key];
                }
            };
            
        }
    ]);
