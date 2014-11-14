'use strict';

angular.module('fmuClientApp')
    .controller('UtredningController', ['$scope', '$filter', '$stateParams', 'AuthService', 'ngDialog', 'UTREDNING_TABLE',
        function ($scope, $filter, $stateParams, AuthService, ngDialog, UTREDNING_TABLE) {
            $scope.authService = AuthService;
            $scope.dateKey = 'creationTime';
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.endDate.setMonth($scope.startDate.getMonth() + 1);
            $scope.dateKey = 'dateOfEvent';

            $scope.currentEavropId = $stateParams.eavropId;
            function getTimeHHMM(hour, minut) {
                var hh = hour < 10 ? '0' + hour : hour;
                var mm = minut < 10 ? '0' + minut : minut;

                return hh + ' : ' + mm;
            };

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

            $scope.getTableCellValue = function (key, rowData) {
                var celldata = rowData[key];
                if (celldata == null) {
                    return '-';
                }
                switch (key) {
                    case 'timeOfEvent' :
                        return getTimeHHMM(celldata.hour,celldata.minute);
                    case 'dateOfEvent':
                        return $filter('date')(celldata, UTREDNING_TABLE.dateFormat);
                    case 'tolkStatus' :
                    case 'handelseStatus' :
                        return UTREDNING_TABLE.handelseMapping[celldata.currentStatus];
                    case 'handelse':
                        return UTREDNING_TABLE.statusMapping[celldata];
                    default :
                        return celldata;
                }
            };

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
