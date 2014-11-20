'use strict';

angular.module('fmuClientApp')
    .controller('CompletedController', ['$scope', '$filter', 'AuthService', 'EAVROP_STATUS', 'EAVROP_TABLE',
        function ($scope, $filter, AuthService, EAVROP_STATUS, EAVROP_TABLE) {
            $scope.authService = AuthService;
            $scope.dateKey = 'creationTime';
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.endDate.setMonth($scope.startDate.getMonth() + 1);
            $scope.startDate.setMonth($scope.startDate.getMonth() - 1);
            $scope.dateKey = 'dateDelivered';

            $scope.completedStatus = EAVROP_STATUS.completed;
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
                    key: 'dagarFromStartToAccepted',
                    name: 'Antal dgr klar'
                },
                {
                    key: 'totalCompletionDays',
                    name: 'Antal dgr för komplettering'
                },
                {
                    key: 'avikelser',
                    name: 'avikelser'
                },

                {
                    key: 'utredareOrganisation',
                    name: 'Utredare, organisation'
                },
                {
                    key: 'ansvarigUtredare',
                    name: 'Utredare, ansvarig'
                },
                {
                    key: 'dateIntygDelivered',
                    name: 'Intyg levererades, datum'
                },
                {
                    key: 'isCompleted',
                    name: 'Utredning komplett ?'
                },
                {
                    key: 'compensationApprovedStatusAndDate',
                    name: 'Godkänd för ersättning'
                }
            ];
            $scope.headerGroups = [
                {
                    name: null,
                    colorClass: null,
                    colspan: 10
                }
            ];

            $scope.footerHints = [
                {
                    description: 'Utredningen är ej komplett, ej godkänd, försenad eller innehåller ersättningsbara avvikelser',
                    colorClass: 'bg-danger'
                },
                {
                    description: 'Utredning komplett, inväntar godkännande för ersättning',
                    colorClass: 'bg-warning'
                },
                {
                    description: 'Utredning är komplett och godkänd',
                    colorClass: 'bg-success'
                }
            ];

            $scope.datePickerDescription = 'Datumen utgår från det datum då intyg levererats';

            $scope.visa = function () {
                if($scope.tableParameters){
                    $scope.tableParameters.reload();
                }
            };

            $scope.getTableCellValue = function (key, rowData) {
                switch (key) {
                    case 'dateIntygDelivered':
                        return $filter('date')(rowData[key], EAVROP_TABLE.dateFormat);
                    case 'isCompleted':
                        return EAVROP_TABLE.isCompletedMapping[rowData[key]];
                    default:
                        return rowData[key];
                }
            }
        }
    ]);
