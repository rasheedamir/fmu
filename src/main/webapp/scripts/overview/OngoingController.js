'use strict';

angular.module('fmuClientApp')
    .controller('OngoingController', ['$scope', 'AuthService', 'EAVROP_STATUS',
        function ($scope, AuthService, EAVROP_STATUS) {
            $scope.authService = AuthService;
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.endDate.setMonth($scope.startDate.getMonth() + 1);
            $scope.dateKey = 'startDate';

            $scope.ongoingStatus = EAVROP_STATUS.accepted;

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
                    key: 'startDate',
                    name: 'Utredning start',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'nrOfDaysSinceStart',
                    name: 'Antal dagar från start',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'avikelser',
                    name: 'Avikelser',
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
        }
    ]);
