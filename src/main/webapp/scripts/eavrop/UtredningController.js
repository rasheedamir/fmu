'use strict';

angular.module('fmuClientApp')
    .controller('UtredningController', ['$scope', '$stateParams', 'AuthService', 'ngDialog', 'EAVROP_STATUS',
        function ($scope, $stateParams, AuthService, ngDialog, EAVROP_STATUS) {
            $scope.authService = AuthService;
            $scope.dateKey = 'creationTime';
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.endDate.setMonth($scope.startDate.getMonth() + 1);
            $scope.dateKey = 'dateOfEvent';

            $scope.currentEavropId = $stateParams.eavropId;

            $scope.headerFields = [
                {
                    key: 'handelse',
                    name: 'Händelse',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'dateOfEvent',
                    name: 'Datum',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'timeOfEvent',
                    name: 'Tidpunkt',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'utredaPerson',
                    name: 'Person',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },
                {
                    key: 'role',
                    name: 'Roll',
                    restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                },

                {
                    key: 'tolkStatus',
                    name: 'Tolk',
                    restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                },
                {
                    key: 'handelseStatus',
                    name: 'Status',
                    restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                },
                {
                    key: 'edit',
                    name: 'Ändra',
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

            $scope.saveHandelse = function () {
                 console.log('save');
                ngDialog.close();
            };

            $scope.cancelHandelse = function () {
                console.log('avbryt');
                ngDialog.close();
            };
        }
    ]);
