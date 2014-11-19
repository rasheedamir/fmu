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

            $scope.headerFields = UtredningService.getTableFields();
            $scope.dateDescription = 'Datumen utgår från det datum då intyg levererats';
            $scope.getTableCellValue = UtredningService.getTableCellValue;

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
                }, function () {
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
                    additionalService: $scope.tillaggRadio,
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
