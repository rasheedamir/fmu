'use strict';

/* Controllers */
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
              if($scope.tableParameters){
                  $scope.tableParameters.reload();
              }
            };

            $scope.bestallningarStatus = EAVROP_STATUS.notAccepted;

            $scope.headerFields = [
                {
                    key: 'arendeId',
                    name: 'Ärende-ID',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'utredningType',
                    name: 'Typ',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'bestallareOrganisation',
                    name: 'Organisation',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'bestallareEnhet',
                    name: 'Enhet/Avdelning',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'creationTime',
                    name: 'Förfrågan skickad datum',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'patientCity',
                    name: 'Den försäkrades bostadsort',
                    restricted: ['ROLE_SAMORDNARE']
                },
                {
                    key: 'mottagarenOrganisation',
                    name: 'Organisation',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'status',
                    name: 'Status',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'antalDagarEfterForfragan',
                    name: 'Antal dagar efter förfrågan om utredning',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
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
                    colspan: 1
                },
                {
                    name: null,
                    colorClass: null,
                    colspan: 2
                }
            ];


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
                        return rowData[key];
                }
            };
        }
    ]);
