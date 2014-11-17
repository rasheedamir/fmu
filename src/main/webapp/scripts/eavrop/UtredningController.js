'use strict';

angular.module('fmuClientApp')
    .controller('UtredningController', ['$scope', '$filter', '$stateParams', 'AuthService', 'UtredningService', 'ngDialog', 'UTREDNING',
        function ($scope, $filter, $stateParams, AuthService, UtredningService, ngDialog, UTREDNING) {
            $scope.authService = AuthService;
            $scope.dateKey = 'creationTime';
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.endDate.setMonth($scope.startDate.getMonth() + 1);
            $scope.dateKey = 'dateOfEvent';

            $scope.handelseDate = new Date();
            $scope.handelseStartTime = new Date();
            $scope.handelseStartTime.setMinutes(0);
            $scope.handelseEndTime = new Date();
            $scope.handelseEndTime.setMinutes(0);
            $scope.currentEavropId = $stateParams.eavropId;
            $scope.createBookingErrors = [];
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
                        return getTimeHHMM(celldata.hour, celldata.minute);
                    case 'dateOfEvent':
                        return $filter('date')(celldata, UTREDNING.dateFormat);
                    case 'tolkStatus' :
                        return UTREDNING.tolkMapping[celldata.currentStatus];
                    case 'handelseStatus' :
                        return UTREDNING.handelseMapping[celldata.currentStatus];
                    case 'handelse':
                        return UTREDNING.statusMapping[celldata];
                    default :
                        return celldata;
                }
            };

            $scope.visa = function () {
                console.log($scope);
                if ($scope.tableParameters) {
                    $scope.tableParameters.reload();
                }
            };

            $scope.tillIntyg = function () {
                console.log('till intyg');
            };

            $scope.handelseTypes = [
                {type: UTREDNING.editableEvents.examination, name: 'Undersökning'},
                {type: UTREDNING.editableEvents.briefing, name: 'Genomgång med patient'},
                {type: UTREDNING.editableEvents.internalWork, name: 'Internt arbete'}
            ];

            $scope.roles = [
                {name: 'Läkare'},
                {name: 'Psykolog'},
                {name: 'Arbetsterapeut'},
                {name: 'Sjukgymnast'},
                {name: 'Utredare'}
            ];

            $scope.saveHandelse = function () {
                var dataPackage = constructBookingObject();
                console.log(dataPackage);
                var promise = UtredningService.createBooking(dataPackage);
                promise.then(function () {
                    ngDialog.close();
                    $scope.tableParameters.reload();
                }, function (error) {
                    $scope.createBookingErrors.push(UTREDNING.errors.cannotCreateBooking);
                });
            };

            $scope.openBookingCreationDialog = function () {
                ngDialog.open({
                    template: 'views/templates/laggTillHandelseDialog.html',
                    scope: $scope
                });
            };

            function constructBookingObject() {
                    return {
                        eavropId: $scope.currentEavropId,
                        bookingType: $scope.choosenHandelseType ? $scope.choosenHandelseType.type : null,
                        bookingDate: $scope.handelseDate.getTime(),
                        bookingStartTime: {
                            hour: $scope.handelseStartTime.getHours(),
                            minute: $scope.handelseStartTime.getMinutes()
                        },
                        bookingEndTime: {
                            hour: $scope.handelseEndTime.getHours(),
                            minute: $scope.handelseEndTime.getMinutes()
                        },
                        personName: $scope.personName,
                        personRole: $scope.choosenRole ? $scope.choosenRole.name : null,
                        personOrganisation: 'Implement this',
                        personUnit: 'Implement this',
                        useInterpreter: $scope.tolkRadio
                    }
            }
        }
    ])
;
