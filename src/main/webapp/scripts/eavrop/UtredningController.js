'use strict';

angular.module('fmuClientApp')
    .controller('UtredningController', ['$scope', 'AuthService', 'EAVROP_STATUS',
        function ($scope, AuthService, EAVROP_STATUS) {
            $scope.authService = AuthService;
            $scope.dateKey = 'creationTime';
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.endDate.setMonth($scope.startDate.getMonth() + 1);
            $scope.dateKey = 'dateDelivered';

            $scope.headerFields = [
                {
                    key: 'arendeId',
                    name: 'Händelse',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'utredningType',
                    name: 'Datum',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'totalDaysPassed',
                    name: 'Tidpunkt',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'totalCompletionDays',
                    name: 'Person',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'avikelser',
                    name: 'Roll',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },

                {
                    key: 'UtredareOrganisation',
                    name: 'Tolk ?',
                    restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                },
                {
                    key: 'utredareAnsvarig',
                    name: 'Status',
                    restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                }
            ];

            $scope.dateDescription = 'Datumen utgår från det datum då intyg levererats';

            $scope.visa = function () {
                if ($scope.tableParameters) {
                    $scope.tableParameters.reload();
                }
            };

            $scope.tillIntyg = function () {
                console.log('till intyg');
            };

            $scope.handelseTypes = [
                {type: 'Besök'},
                {type: 'Tolk'}
            ];

            $scope.roles = [
                {name: 'Läkare'},
                {name: 'Psykolog'},
                {name: 'Arbetsterapeut'},
                {name: 'Sjukgymnast'},
                {name: 'Utredare'}
            ];
        }
    ]);
