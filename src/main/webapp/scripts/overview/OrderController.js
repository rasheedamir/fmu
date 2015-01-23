'use strict';

angular.module('fmuClientApp')
    .controller('OrderController', ['$scope', '$state', '$filter', 'AuthService', 'EAVROP_STATUS', 'EAVROP_TABLE',
        function ($scope, $state, $filter, AuthService, EAVROP_STATUS, EAVROP_TABLE) {
            $scope.authService = AuthService;
            $scope.dateKey = 'creationTime';
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.endDate.setMonth($scope.startDate.getMonth() + 1);
            $scope.startDate.setMonth($scope.startDate.getMonth() - 1);

            $scope.click = function (id) {
                $state.go('eavrop.order', {eavropId: id});
            };

            $scope.visa = function () {
                if ($scope.tableParameters) {
                    $scope.tableParameters.reload();
                }
            };

            $scope.bestallningarStatus = EAVROP_STATUS.notAccepted;

            $scope.initTableParameters = function () {
                if (AuthService.hasRole('ROLE_SAMORDNARE')) {
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
                            key: 'creationTime',
                            name: 'Förfrågan inkommit datum'
                        },
                        {
                            key: 'patientCity',
                            name: 'Den försäkrades bostadsort'
                        },
                        {
                            key: 'mottagarenOrganisation',
                            name: 'Organisation'
                        },
                        {
                            key: 'utredareOrganisation',
                            name: 'Enhet'
                        },
                        {
                            key: 'status',
                            name: 'Status'
                        },
                        {
                            key: 'antalDagarEfterForfragan',
                            name: 'Antal dagar efter förfrågan om utredning'
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
                            colspan: 4
                        },
                        {
                            name: 'leverantör',
                            colorClass: 'bg-head-warning',
                            colspan: 2
                        },
                        {
                            name: null,
                            colorClass: null,
                            colspan: 2
                        }
                    ];
                } else {
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
                            key: 'creationTime',
                            name: 'Förfrågan inkommit datum'
                        },
                        {
                            key: 'mottagarenOrganisation',
                            name: 'Organisation'
                        },
                        {
                            key: 'utredareOrganisation',
                            name: 'Enhet'
                        },
                        {
                            key: 'status',
                            name: 'Status'
                        },
                        {
                            key: 'antalDagarEfterForfragan',
                            name: 'Antal dagar efter förfrågan om utredning'
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
                            colspan: 3
                        },
                        {
                            name: 'leverantör',
                            colorClass: 'bg-head-warning',
                            colspan: 2
                        },
                        {
                            name: null,
                            colorClass: null,
                            colspan: 2
                        }
                    ];
                }
            };

            $scope.footerHints = [
                {
                    description: 'Antal dagar har överträtts och/eller annan avvikelse finns',
                    colorClass: 'bg-danger'
                }
            ];

            $scope.datePickerDescription = 'Datumen utgår från det datum då beställningen inkommit';

            $scope.getTableCellValue = function (key, rowData) {
                switch (key) {
                    case 'creationTime':
                        return $filter('date')(rowData[key], EAVROP_TABLE.dateFormat);
                    case 'status':
                        return EAVROP_TABLE.statusMapping[rowData[key]];
                    default:
                        return rowData[key] ? rowData[key] : '&nbsp;';
                }
            };

            // TODO fix this with event ?
            $scope.$watch('authService', function () {
                $scope.initTableParameters();
            }, true);
        }
    ]);
