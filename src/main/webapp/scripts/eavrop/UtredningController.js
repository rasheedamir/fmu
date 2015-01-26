'use strict';

angular.module('fmuClientApp')
    .controller('UtredningController', ['$scope', '$filter', '$stateParams', 'AuthService', 'UtredningService', '$modal', 'currentEavrop',
        function ($scope, $filter, $stateParams, AuthService, UtredningService, $modal, currentEavrop) {
            $scope.authService = AuthService;
            $scope.currentEavropId = $stateParams.eavropId;

            $scope.headerFields = UtredningService.getTableFields();
            $scope.getTableCellValue = UtredningService.getTableCellValue;

            $scope.openBookingCreationDialog = function () {
                var modal = $modal.open({
                    templateUrl: 'views/templates/laggTillHandelseDialog.html',
                    resolve: {
                        currentEavrop: function () {
                            return currentEavrop;
                        },

                        tableParameters: function () {
                            return $scope.tableParameters;
                        }
                    },
                    controller: function ($scope, currentEavrop, tableParameters) {
                        $scope.handelseDate = new Date();
                        $scope.handelseStartTime = new Date();
                        $scope.handelseStartTime.setMinutes(0);
                        $scope.handelseEndTime = new Date();
                        $scope.handelseEndTime.setMinutes(0);
                        $scope.createBookingErrors = [];
                        $scope.tillaggRadio = {value: false};
                        $scope.tolkRadio = false;

                        $scope.handelseTypes = UtredningService.getTextConstants().handelseTypes;
                        $scope.roles = UtredningService.getTextConstants().roles;

                        $scope.isAFU = function () {
                             return currentEavrop && currentEavrop.utredningType === 'AFU';
                        };

                        function constructBookingObject() {
                            return {
                                eavropId: currentEavrop.eavropId,
                                bookingType: $scope.choosenHandelseType ? $scope.choosenHandelseType.type : null,
                                bookingDate: $scope.handelseDate.getTime(),
                                bookingStartTime: {
                                    hour: $scope.handelseStartTime.getHours(),
                                    minute: $scope.handelseStartTime.getMinutes()
                                },
                                additionalService: $scope.tillaggRadio.value,
                                bookingEndTime: {
                                    hour: $scope.handelseEndTime.getHours(),
                                    minute: $scope.handelseEndTime.getMinutes()
                                },
                                personName: $scope.personName,
                                personRole: $scope.choosenRole ? $scope.choosenRole.name : null,
                                useInterpreter: $scope.tolkRadio
                            };
                        }

                        $scope.saveHandelse = function () {
                            var dataPackage = constructBookingObject();
                            var promise = UtredningService.createBooking(dataPackage);
                            promise.then(function () {
                                modal.close();
                                tableParameters.reload();
                            }, function () {
                                $scope.createBookingErrors.push(UtredningService.getTextConstants().errors.cannotCreateBooking);
                            });
                        };

                        $scope.cancel = function () {
                            modal.dismiss('cancel');
                        };
                    }
                });
            };
        }
    ])
;
