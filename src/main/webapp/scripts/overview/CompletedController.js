'use strict';

angular.module('fmuClientApp')
    .controller('CompletedController', ['$scope', '$filter', 'AuthService', 'DatetimeService', 'EAVROP_STATUS', 'EAVROP_TABLE',
        function ($scope, $filter, AuthService, DatetimeService, EAVROP_STATUS, EAVROP_TABLE) {
            $scope.authService = AuthService;
            $scope.dateKey = 'creationTime';
            $scope.datetimeService = DatetimeService;

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
                    name: 'Utredning komplett&nbsp;?'
                },
                {
                    key: 'isCompensationApproved',
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
                    description: 'Invänta acceptans av intyg och godkännande för ersättning',
                    colorClass: null
                },
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
                var value = rowData[key];
                switch (key) {
                    case 'dateIntygDelivered':
                        return $filter('date')(value, EAVROP_TABLE.dateFormat);
                    case 'isCompleted':
                        return EAVROP_TABLE.isCompletedMapping[value];
                    case 'isCompensationApproved':
                        var status = EAVROP_TABLE.isCompensationApprovedMapping[value];
                        if(value === null){
                            return status;
                        } else {
                            return status + ', ' + $filter('date')(rowData.compensationApprovalDate, EAVROP_TABLE.dateFormat);
                        }
                        break;
                    default:
                        return value === null ? '-' : value;
                }
            };
        }
    ]);
